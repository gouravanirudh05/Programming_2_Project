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
import { Dish } from './page'

type DishManagementProps = {
  dishes: Dish[]
  setDishes: React.Dispatch<React.SetStateAction<Dish[]>>
}

export default function DishManagement({ dishes, setDishes }: DishManagementProps) {
  const [newDish, setNewDish] = useState<Dish>({ dishID: '', name: '', price: 0, dishType: '' })
  const [isAddDishOpen, setIsAddDishOpen] = useState(false)
  const [editingDish, setEditingDish] = useState<Dish | null>(null)

  const addDish = () => {
    setDishes([...dishes, { ...newDish, dishID: `D${dishes.length + 1}` }])
    setNewDish({ dishID: '', name: '', price: 0, dishType: '' })
    setIsAddDishOpen(false)
  }

  const updateDish = () => {
    if (editingDish) {
      setDishes(dishes.map(dish => dish.dishID === editingDish.dishID ? editingDish : dish))
      setEditingDish(null)
    }
  }

  const deleteDish = (dishID: string) => {
    setDishes(dishes.filter(dish => dish.dishID !== dishID))
  }

  return (
    <div>
      <div className="mb-4">
        <Dialog open={isAddDishOpen} onOpenChange={setIsAddDishOpen}>
          <DialogTrigger asChild>
            <Button>Add Dish</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New Dish</DialogTitle>
              <DialogDescription>Enter the details for the new dish.</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="name" className="text-right">Name</Label>
                <Input
                  id="name"
                  value={newDish.name}
                  onChange={(e) => setNewDish({ ...newDish, name: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="price" className="text-right">Price</Label>
                <Input
                  id="price"
                  type="number"
                  value={newDish.price}
                  onChange={(e) => setNewDish({ ...newDish, price: parseFloat(e.target.value) })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="dishType" className="text-right">Type</Label>
                <Select onValueChange={(value) => setNewDish({ ...newDish, dishType: value })}>
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select dish type" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="Appetizer">Appetizer</SelectItem>
                    <SelectItem value="Main Course">Main Course</SelectItem>
                    <SelectItem value="Dessert">Dessert</SelectItem>
                    <SelectItem value="Beverage">Beverage</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </div>
            <DialogFooter>
              <Button onClick={addDish}>Add Dish</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>ID</TableHead>
            <TableHead>Name</TableHead>
            <TableHead>Price</TableHead>
            <TableHead>Type</TableHead>
            <TableHead>Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {dishes.map((dish) => (
            <TableRow key={dish.dishID}>
              <TableCell>{dish.dishID}</TableCell>
              <TableCell>{dish.name}</TableCell>
              <TableCell>${dish.price.toFixed(2)}</TableCell>
              <TableCell>{dish.dishType}</TableCell>
              <TableCell>
                <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingDish(dish)}>
                  Edit
                </Button>
                <Button variant="destructive" size="sm" onClick={() => deleteDish(dish.dishID)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      {editingDish && (
        <Dialog open={!!editingDish} onOpenChange={() => setEditingDish(null)}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit Dish</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-name" className="text-right">Name</Label>
                <Input
                  id="edit-name"
                  value={editingDish.name}
                  onChange={(e) => setEditingDish({ ...editingDish, name: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-price" className="text-right">Price</Label>
                <Input
                  id="edit-price"
                  type="number"
                  value={editingDish.price}
                  onChange={(e) => setEditingDish({ ...editingDish, price: parseFloat(e.target.value) })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-dishType" className="text-right">Type</Label>
                <Select 
                  value={editingDish.dishType} 
                  onValueChange={(value) => setEditingDish({ ...editingDish, dishType: value })}
                >
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select dish type" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="Appetizer">Appetizer</SelectItem>
                    <SelectItem value="Main Course">Main Course</SelectItem>
                    <SelectItem value="Dessert">Dessert</SelectItem>
                    <SelectItem value="Beverage">Beverage</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </div>
            <DialogFooter>
              <Button onClick={updateDish}>Update Dish</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  )
}