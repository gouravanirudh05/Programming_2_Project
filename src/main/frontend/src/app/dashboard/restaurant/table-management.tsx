import { useState } from 'react'
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
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { Table as TableType } from './page'

type TableManagementProps = {
  tables: TableType[]
  setTables: React.Dispatch<React.SetStateAction<TableType[]>>
}

export default function TableManagement({ tables, setTables }: TableManagementProps) {
  const [newTable, setNewTable] = useState<TableType>({ 
    tableID: '', 
    capacity: 0, 
    occupied: false, 
    occupiedBy: null, 
    orderStatus: 'Vacant' 
  })
  const [isAddTableOpen, setIsAddTableOpen] = useState(false)
  const [editingTable, setEditingTable] = useState<TableType | null>(null)

  const addTable = () => {
    setTables([...tables, { ...newTable, tableID: `T${tables.length + 1}` }])
    setNewTable({ tableID: '', capacity: 0, occupied: false, occupiedBy: null, orderStatus: 'Vacant' })
    setIsAddTableOpen(false)
  }

  const updateTable = () => {
    if (editingTable) {
      setTables(tables.map(table => table.tableID === editingTable.tableID ? editingTable : table))
      setEditingTable(null)
    }
  }

  const deleteTable = (tableID: string) => {
    setTables(tables.filter(table => table.tableID !== tableID))
  }

  return (
    <div>
      <div className="mb-4">
        <Dialog open={isAddTableOpen} onOpenChange={setIsAddTableOpen}>
          <DialogTrigger asChild>
            <Button>Add Table</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New Table</DialogTitle>
              <DialogDescription>Enter the details for the new table.</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="capacity" className="text-right">Capacity</Label>
                <Input
                  id="capacity"
                  type="number"
                  value={newTable.capacity}
                  onChange={(e) => setNewTable({ ...newTable, capacity: parseInt(e.target.value) })}
                  className="col-span-3"
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={addTable}>Add Table</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>ID</TableHead>
            <TableHead>Capacity</TableHead>
            <TableHead>Status</TableHead>
            <TableHead>Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {tables.map((table) => (
            <TableRow key={table.tableID}>
              <TableCell>{table.tableID}</TableCell>
              <TableCell>{table.capacity}</TableCell>
              <TableCell>{table.orderStatus}</TableCell>
              <TableCell>
                <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingTable(table)}>
                  Edit
                </Button>
                <Button variant="destructive" size="sm" onClick={() => deleteTable(table.tableID)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      {editingTable && (
        <Dialog open={!!editingTable} onOpenChange={() => setEditingTable(null)}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit Table</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-capacity" className="text-right">Capacity</Label>
                <Input
                  id="edit-capacity"
                  type="number"
                  value={editingTable.capacity}
                  onChange={(e) => setEditingTable({ ...editingTable, capacity: parseInt(e.target.value) })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-status" className="text-right">Status</Label>
                <Select 
                  value={editingTable.orderStatus} 
                  onValueChange={(value: 'Vacant' | 'Occupied' | 'Preparing') => 
                    setEditingTable({ ...editingTable, orderStatus: value, occupied: value !== 'Vacant' })
                  }
                >
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select status" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="Vacant">Vacant</SelectItem>
                    <SelectItem value="Occupied">Occupied</SelectItem>
                    <SelectItem value="Preparing">Preparing</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </div>
            <DialogFooter>
              <Button onClick={updateTable}>Update Table</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  )
}