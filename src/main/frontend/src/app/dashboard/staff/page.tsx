"use client"
import { useState, useEffect } from 'react'
import axios from 'axios'

import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { toast } from '@/hooks/use-toast'
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

export default function StaffManagement() {
  const [staff, setStaff] = useState<Staff[]>([])
  const [newStaff, setNewStaff] = useState<Staff>({
    staffID: '',
    name: '',
    salary: 0,
    phone: '',
    address: '',
    role: '',
    workingFrom: '',
    retiredOn: '',
    assignedTo: '',
  })
  const [isAddStaffOpen, setIsAddStaffOpen] = useState(false)
  const [editingStaff, setEditingStaff] = useState<Staff | null>(null)

  // Fetch staff data from the backend
  const fetchStaff = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/staff/list') // Replace with your backend endpoint
      for (let i = 0; i < response.data.length; i++) {
        response.data[i].workingFrom = new Date(response.data[i].workingFrom.dateString+" "+response.data[i].workingFrom.timeString).toISOString()
        response.data[i].retiredOn = new Date(response.data[i].retiredOn.dateString+" "+response.data[i].retiredOn.timeString).toISOString()
      }
      setStaff(response.data)
    } catch (error) {
      console.error('Error fetching staff:', error)
    }
  }

  // Add new staff
  const addStaff = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/staff/add', newStaff) // Replace with your backend endpoint
      setStaff([...staff, response.data])
      setNewStaff({
        staffID: '',
        name: '',
        salary: 0,
        phone: '',
        address: '',
        role: '',
        workingFrom: '',
        retiredOn: '',
        assignedTo: '',
      })
      setIsAddStaffOpen(false)
    } catch (error) {
      console.error('Error adding staff:', error)
    }
  }

  // Update staff details
  const updateStaff = async () => {
    if (!editingStaff) return
    try {
      const response = await axios.put(`http://localhost:8080/api/staff/update/${editingStaff.staffID}`, editingStaff) // Replace with your backend endpoint
      setStaff(staff.map((s) => (s.staffID === editingStaff.staffID ? response.data : s)))
      setEditingStaff(null)
    } catch (error) {
      console.error('Error updating staff:', error)
    }
  }

  // Delete a staff member
  const deleteStaff = async (id: string) => {
    try {
      await axios.post(`http://localhost:8080/api/staff/remove/${id}`) // Replace with your backend endpoint
      setStaff(staff.filter((s) => s.staffID !== id))
    } catch (error) {
      console.error('Error deleting staff:', error)
    }
  }

  // Load staff data on component mount
  useEffect(() => {
    fetchStaff()
  }, [])

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
                  onChange={(e) => setNewStaff({ ...newStaff, salary: +e.target.value })}
                  className="col-span-3"
                />
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
                <Label htmlFor="role" className="text-right">Role</Label>
                <Input
                  id="role"
                  value={newStaff.role}
                  onChange={(e) => setNewStaff({ ...newStaff, role: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-workingfrom" className="text-right">Working From</Label>
                <Input
                  id="edit-workingfrom"
                  type="datetime-local"
                  value={newStaff.workingFrom.slice(0, 16)}
                  onChange={(e) => setNewStaff({
                    ...newStaff,
                    workingFrom: new Date(e.target.value).toISOString()
                  })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-retiredon" className="text-right">Retired On</Label>
                <Input
                  id="edit-retiredon"
                  type="datetime-local"
                  value={newStaff.retiredOn.slice(0, 16)}
                  onChange={(e) => setNewStaff({
                    ...newStaff,
                    retiredOn: new Date(e.target.value).toISOString()
                  })}
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
                <TableHead>Phone</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {staff.map((s) => (
                <TableRow key={s.staffID}>
                  <TableCell>{s.staffID}</TableCell>
                  <TableCell>{s.name}</TableCell>
                  <TableCell>{s.role}</TableCell>
                  <TableCell>{s.phone}</TableCell>
                  <TableCell>
                    <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingStaff(s)}>
                      Edit
                    </Button>
                    <Button variant="destructive" size="sm" onClick={() => deleteStaff(s.staffID)}>
                      Delete
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
            {/* {JSON.stringify(editingStaff)} */}
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
                <Label htmlFor="edit-role" className="text-right">Role</Label>
                <Input
                  id="edit-role"
                  value={editingStaff.role}
                  onChange={(e) => setEditingStaff({ ...editingStaff, role: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-retiredon" className="text-right">Retired On</Label>
                <Input
                  id="edit-retiredon"
                  type="datetime-local"
                  value={editingStaff.retiredOn.slice(0, 16)}
                  onChange={(e) => setEditingStaff({
                    ...editingStaff,
                    retiredOn: new Date(e.target.value).toISOString()
                  })}
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
