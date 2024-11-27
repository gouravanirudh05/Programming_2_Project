'use client'

import { useState, useEffect } from 'react'
import axios from 'axios'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
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
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'

type Table = {
  tableNumber: number
  isOccupied: boolean
  seatingCapacity: number
}

export default function TableManagement() {
  const [tables, setTables] = useState<Table[]>([])
  const [newTable, setNewTable] = useState<Partial<Table>>({})
  const [isNewTableDialogOpen, setIsNewTableDialogOpen] = useState(false)

  useEffect(() => {
    fetchTables()
  }, [])

  const fetchTables = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/table/all')
      setTables(response.data)
    } catch (error) {
      console.error('Error fetching tables:', error)
    }
  }

  const handleNewTable = async () => {
    try {
      await axios.post('http://localhost:8080/api/table/add', newTable)
      fetchTables()
      setIsNewTableDialogOpen(false)
      setNewTable({})
    } catch (error) {
      console.error('Error creating new table:', error)
    }
  }
  const handleDeleteTable = async (id) => {
    try {
      await axios.post('http://localhost:8080/api/table/remove/' + id)
      fetchTables()
    } catch (error) {
      console.error('Error deleting table:', error)
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold tracking-tight">Table Management</h2>
        <Dialog open={isNewTableDialogOpen} onOpenChange={setIsNewTableDialogOpen}>
          <DialogTrigger asChild>
            <Button onClick={() => setIsNewTableDialogOpen(true)}>New Table</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New Table</DialogTitle>
              <DialogDescription>Enter the details for the new table.</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="tableNumber" className="text-right">
                  Table Number
                </Label>
                <Input
                  id="tableNumber"
                  type="number"
                  className="col-span-3"
                  value={newTable.tableNumber || ''}
                  onChange={(e) => setNewTable({ ...newTable, tableNumber: parseInt(e.target.value) })}
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="seatingCapacity" className="text-right">
                  Seating Capacity
                </Label>
                <Input
                  id="seatingCapacity"
                  type="number"
                  className="col-span-3"
                  value={newTable.seatingCapacity || ''}
                  onChange={(e) => setNewTable({ ...newTable, seatingCapacity: parseInt(e.target.value) })}
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={handleNewTable}>Add Table</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>All Tables</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Table Number</TableHead>
                <TableHead>Seating Capacity</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {tables.map((table) => (
                <TableRow key={table.tableNumber}>
                  <TableCell>{table.tableNumber}</TableCell>
                  <TableCell>{table.seatingCapacity}</TableCell>
                  <TableCell>{table.isOccupied ? 'Occupied' : 'Available'}</TableCell>
                  <TableCell>
                    {/* <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingTable(table)}>
                      Edit
                    </Button> */}
                    <Button variant="outline" size="sm" className="mr-2" onClick={() => handleDeleteTable(table.tableNumber)}>
                      Delete
                    </Button>
                    </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  )
}