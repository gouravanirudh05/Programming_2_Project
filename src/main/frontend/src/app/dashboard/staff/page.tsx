'use client'

import { useState, useEffect } from 'react'
import axios from 'axios'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { toast } from '@/hooks/use-toast'

// API configuration
const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:3000/api',
  headers: {
    'Content-Type': 'application/json',
  }
})

interface ApiResponse<T> {
  status: 'success' | 'error'
  data: T
  message: string
}

type Staff = {
  staffID: string
  name: string
  salary: number
  phone: string
  address: string
  role: string
  workingFrom: string
  retiredOn: string | null
  assignedTo: string
}

const ROLES = ['Manager', 'Front Desk', 'Housekeeping', 'Maintenance', 'Restaurant']

const emptyStaff: Staff = {
  staffID: '',
  name: '',
  salary: 0,
  phone: '',
  address: '',
  role: '',
  workingFrom: '',
  retiredOn: null,
  assignedTo: ''
}

export default function StaffManagement() {
  const [staff, setStaff] = useState<Staff[]>([])
  const [newStaff, setNewStaff] = useState<Staff>(emptyStaff)
  const [isAddStaffOpen, setIsAddStaffOpen] = useState(false)
  const [editingStaff, setEditingStaff] = useState<Staff | null>(null)
  const [isLoading, setIsLoading] = useState(true)

  // Fetch staff data on component mount
  useEffect(() => {
    fetchStaff()
  }, [])

  const fetchStaff = async () => {
    try {
      const response = await api.get<ApiResponse<Staff[]>>('/staff')
      if (response.data.status === 'success') {
        setStaff(response.data.data)
      } else {
        toast({
          title: "Error",
          description: response.data.message || "Failed to fetch staff data",
          variant: "destructive"
        })
      }
    } catch (error) {
      toast({
        title: "Error",
        description: axios.isAxiosError(error) 
          ? error.response?.data?.message || error.message 
          : "An error occurred while fetching staff data",
        variant: "destructive"
      })
    } finally {
      setIsLoading(false)
    }
  }

  const addStaff = async () => {
    try {
      const response = await api.post<ApiResponse<Staff>>('/staff', newStaff)
      
      if (response.data.status === 'success') {
        setStaff([...staff, response.data.data])
        setNewStaff(emptyStaff)
        setIsAddStaffOpen(false)
        toast({
          title: "Success",
          description: "Staff member added successfully",
        })
      } else {
        toast({
          title: "Error",
          description: response.data.message || "Failed to add staff member",
          variant: "destructive"
        })
      }
    } catch (error) {
      toast({
        title: "Error",
        description: axios.isAxiosError(error)
          ? error.response?.data?.message || error.message
          : "An error occurred while adding staff member",
        variant: "destructive"
      })
    }
  }

  const updateStaff = async () => {
    if (!editingStaff) return

    try {
      const response = await api.post<ApiResponse<Staff>>(
        `/staff/${editingStaff.staffID}`,
        editingStaff
      )

      if (response.data.status === 'success') {
        setStaff(staff.map(s => s.staffID === response.data.data.staffID ? response.data.data : s))
        setEditingStaff(null)
        toast({
          title: "Success",
          description: "Staff member updated successfully",
        })
      } else {
        toast({
          title: "Error",
          description: response.data.message || "Failed to update staff member",
          variant: "destructive"
        })
      }
    } catch (error) {
      toast({
        title: "Error",
        description: axios.isAxiosError(error)
          ? error.response?.data?.message || error.message
          : "An error occurred while updating staff member",
        variant: "destructive"
      })
    }
  }

  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold tracking-tight">Staff Management</h1>
        <Dialog open={isAddStaffOpen} onOpenChange={setIsAddStaffOpen}>
          <DialogTrigger asChild>
            <Button>Add Staff</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New Staff</DialogTitle>
              <DialogDescription>Enter the details for the new staff member.</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="name" className="text-right">Name</Label>
                <Input
                  id="name"
                  value={newStaff.name}
                  onChange={(e) => setNewStaff({ ...newStaff, name: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="salary" className="text-right">Salary</Label>
                <Input
                  id="salary"
                  type="number"
                  value={newStaff.salary}
                  onChange={(e) => setNewStaff({ ...newStaff, salary: Number(e.target.value) })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="role" className="text-right">Role</Label>
                <Select onValueChange={(value) => setNewStaff({ ...newStaff, role: value })}>
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select role" />
                  </SelectTrigger>
                  <SelectContent>
                    {ROLES.map((role) => (
                      <SelectItem key={role} value={role}>{role}</SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="phone" className="text-right">Phone</Label>
                <Input
                  id="phone"
                  value={newStaff.phone}
                  onChange={(e) => setNewStaff({ ...newStaff, phone: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="address" className="text-right">Address</Label>
                <Input
                  id="address"
                  value={newStaff.address}
                  onChange={(e) => setNewStaff({ ...newStaff, address: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="workingFrom" className="text-right">Working From</Label>
                <Input
                  id="workingFrom"
                  type="date"
                  value={newStaff.workingFrom}
                  onChange={(e) => setNewStaff({ ...newStaff, workingFrom: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="assignedTo" className="text-right">Assigned To</Label>
                <Input
                  id="assignedTo"
                  value={newStaff.assignedTo}
                  onChange={(e) => setNewStaff({ ...newStaff, assignedTo: e.target.value })}
                  className="col-span-3"
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={addStaff}>Add Staff</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Staff List</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Name</TableHead>
                <TableHead>Role</TableHead>
                <TableHead>Salary</TableHead>
                <TableHead>Phone</TableHead>
                <TableHead>Working From</TableHead>
                <TableHead>Assigned To</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {staff.map((s) => (
                <TableRow key={s.staffID}>
                  <TableCell>{s.staffID}</TableCell>
                  <TableCell>{s.name}</TableCell>
                  <TableCell>{s.role}</TableCell>
                  <TableCell>${s.salary.toLocaleString()}</TableCell>
                  <TableCell>{s.phone}</TableCell>
                  <TableCell>{new Date(s.workingFrom).toLocaleDateString()}</TableCell>
                  <TableCell>{s.assignedTo}</TableCell>
                  <TableCell>
                    <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingStaff(s)}>
                      Edit
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {editingStaff && (
        <Dialog open={!!editingStaff} onOpenChange={() => setEditingStaff(null)}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit Staff</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-name" className="text-right">Name</Label>
                <Input
                  id="edit-name"
                  value={editingStaff.name}
                  onChange={(e) => setEditingStaff({ ...editingStaff, name: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-salary" className="text-right">Salary</Label>
                <Input
                  id="edit-salary"
                  type="number"
                  value={editingStaff.salary}
                  onChange={(e) => setEditingStaff({ ...editingStaff, salary: Number(e.target.value) })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-role" className="text-right">Role</Label>
                <Select 
                  value={editingStaff.role} 
                  onValueChange={(value) => setEditingStaff({ ...editingStaff, role: value })}
                >
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select role" />
                  </SelectTrigger>
                  <SelectContent>
                    {ROLES.map((role) => (
                      <SelectItem key={role} value={role}>{role}</SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-phone" className="text-right">Phone</Label>
                <Input
                  id="edit-phone"
                  value={editingStaff.phone}
                  onChange={(e) => setEditingStaff({ ...editingStaff, phone: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-address" className="text-right">Address</Label>
                <Input
                  id="edit-address"
                  value={editingStaff.address}
                  onChange={(e) => setEditingStaff({ ...editingStaff, address: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-workingFrom" className="text-right">Working From</Label>
                <Input
                  id="edit-workingFrom"
                  type="date"
                  value={editingStaff.workingFrom}
                  onChange={(e) => setEditingStaff({ ...editingStaff, workingFrom: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-assignedTo" className="text-right">Assigned To</Label>
                <Input
                  id="edit-assignedTo"
                  value={editingStaff.assignedTo}
                  onChange={(e) => setEditingStaff({ ...editingStaff, assignedTo: e.target.value })}
                  className="col-span-3"
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={updateStaff}>Update Staff</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  )
}