'use client'

import { useState, useEffect } from 'react'
import axios from 'axios'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
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

type RestaurantCustomer = {
  id: string
  name: string
  email: string
  phone: string
  address: string
  tableId: number
  serverId: string
  dishes: string[]
}

type Dish = {
  id: string
  name: string
  price: number
}

type Table = {
  tableNumber: number
  isOccupied: boolean
  seatingCapacity: number
}

export default function OrderManagement() {
  const [customers, setCustomers] = useState<RestaurantCustomer[]>([])
  const [dishes, setDishes] = useState<Dish[]>([])
  const [tables, setTables] = useState<Table[]>([])
  const [newCustomer, setNewCustomer] = useState<Partial<RestaurantCustomer>>({})
  const [selectedCustomer, setSelectedCustomer] = useState<RestaurantCustomer | null>(null)
  const [isNewCustomerDialogOpen, setIsNewCustomerDialogOpen] = useState(false)
  const [isOrderDialogOpen, setIsOrderDialogOpen] = useState(false)
  const [isBillDialogOpen, setIsBillDialogOpen] = useState(false)

  useEffect(() => {
    fetchCustomers()
    fetchDishes()
    fetchTables()
  }, [])

  const fetchCustomers = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/restaurant-customers')
      setCustomers(response.data.filter((customer: RestaurantCustomer) => customer.tableId !== -1))
    } catch (error) {
      console.error('Error fetching customers:', error)
    }
  }

  const fetchDishes = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/dishes')
      setDishes(response.data)
    } catch (error) {
      console.error('Error fetching dishes:', error)
    }
  }

  const fetchTables = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/tables')
      setTables(response.data)
    } catch (error) {
      console.error('Error fetching tables:', error)
    }
  }

  const handleNewCustomer = async () => {
    try {
      const availableTable = tables.find(table => !table.isOccupied)
      if (!availableTable) {
        alert('No tables available')
        return
      }
      const customerData = {
        ...newCustomer,
        tableId: availableTable.tableNumber,
        dishes: []
      }
      await axios.post('http://localhost:8080/api/restaurant-customers', customerData)
      await axios.put(`http://localhost:8080/api/tables/${availableTable.tableNumber}`, { isOccupied: true })
      fetchCustomers()
      fetchTables()
      setIsNewCustomerDialogOpen(false)
      setNewCustomer({})
    } catch (error) {
      console.error('Error creating new customer:', error)
    }
  }

  const handleAddDish = async (dishId: string) => {
    if (!selectedCustomer) return
    try {
      const updatedCustomer = {
        ...selectedCustomer,
        dishes: [...selectedCustomer.dishes, dishId]
      }
      await axios.put(`http://localhost:8080/api/restaurant-customers/${selectedCustomer.id}`, updatedCustomer)
      setSelectedCustomer(updatedCustomer)
      fetchCustomers()
    } catch (error) {
      console.error('Error adding dish:', error)
    }
  }

  const handleRemoveDish = async (dishId: string) => {
    if (!selectedCustomer) return
    try {
      const updatedCustomer = {
        ...selectedCustomer,
        dishes: selectedCustomer.dishes.filter(id => id !== dishId)
      }
      await axios.put(`http://localhost:8080/api/restaurant-customers/${selectedCustomer.id}`, updatedCustomer)
      setSelectedCustomer(updatedCustomer)
      fetchCustomers()
    } catch (error) {
      console.error('Error removing dish:', error)
    }
  }

  const handleClearTable = async (customerId: string, tableId: number) => {
    try {
      await axios.put(`http://localhost:8080/api/restaurant-customers/${customerId}`, { tableId: -1 })
      await axios.put(`http://localhost:8080/api/tables/${tableId}`, { isOccupied: false })
      fetchCustomers()
      fetchTables()
    } catch (error) {
      console.error('Error clearing table:', error)
    }
  }

  const calculateBill = (customer: RestaurantCustomer) => {
    return customer.dishes.reduce((total, dishId) => {
      const dish = dishes.find(d => d.id === dishId)
      return total + (dish ? dish.price : 0)
    }, 0)
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <Dialog open={isNewCustomerDialogOpen} onOpenChange={setIsNewCustomerDialogOpen}>
          <DialogTrigger asChild>
            <Button onClick={() => setIsNewCustomerDialogOpen(true)}>New Customer</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New Customer</DialogTitle>
              <DialogDescription>Enter the details for the new customer.</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="name" className="text-right">
                  Name
                </Label>
                <Input
                  id="name"
                  className="col-span-3"
                  value={newCustomer.name || ''}
                  onChange={(e) => setNewCustomer({ ...newCustomer, name: e.target.value })}
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="phone" className="text-right">
                  Phone
                </Label>
                <Input
                  id="phone"
                  className="col-span-3"
                  value={newCustomer.phone || ''}
                  onChange={(e) => setNewCustomer({ ...newCustomer, phone: e.target.value })}
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={handleNewCustomer}>Add Customer</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>Current Orders</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Customer Name</TableHead>
                <TableHead>Table Number</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {customers.map((customer) => (
                <TableRow key={customer.id}>
                  <TableCell>{customer.name}</TableCell>
                  <TableCell>{customer.tableId}</TableCell>
                  <TableCell>
                    <Button variant="outline" className="mr-2" onClick={() => {
                      setSelectedCustomer(customer)
                      setIsOrderDialogOpen(true)
                    }}>
                      Manage Order
                    </Button>
                    <Button variant="outline" className="mr-2" onClick={() => {
                      setSelectedCustomer(customer)
                      setIsBillDialogOpen(true)
                    }}>
                      Generate Bill
                    </Button>
                    <Button variant="destructive" onClick={() => handleClearTable(customer.id, customer.tableId)}>
                      Clear Table
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <Dialog open={isOrderDialogOpen} onOpenChange={setIsOrderDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Manage Order</DialogTitle>
            <DialogDescription>Add or remove dishes from the order.</DialogDescription>
          </DialogHeader>
          {selectedCustomer && (
            <div className="grid gap-4 py-4">
              <h3>Current Order:</h3>
              <ul>
                {selectedCustomer.dishes.map((dishId) => {
                  const dish = dishes.find(d => d.id === dishId)
                  return dish ? (
                    <li key={dish.id} className="flex justify-between items-center">
                      <span>{dish.name} - ${dish.price}</span>
                      <Button variant="destructive" size="sm" onClick={() => handleRemoveDish(dish.id)}>Remove</Button>
                    </li>
                  ) : null
                })}
              </ul>
              <h3>Add Dish:</h3>
              <Select onValueChange={(value) => handleAddDish(value)}>
                <SelectTrigger>
                  <SelectValue placeholder="Select a dish" />
                </SelectTrigger>
                <SelectContent>
                  {dishes.map((dish) => (
                    <SelectItem key={dish.id} value={dish.id}>
                      {dish.name} - ${dish.price}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          )}
        </DialogContent>
      </Dialog>

      <Dialog open={isBillDialogOpen} onOpenChange={setIsBillDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Generate Bill</DialogTitle>
            <DialogDescription>Review and print the bill for the customer.</DialogDescription>
          </DialogHeader>
          {selectedCustomer && (
            <div className="grid gap-4 py-4">
              <h3>Bill for {selectedCustomer.name}</h3>
              <ul>
                {selectedCustomer.dishes.map((dishId) => {
                  const dish = dishes.find(d => d.id === dishId)
                  return dish ? (
                    <li key={dish.id} className="flex justify-between items-center">
                      <span>{dish.name}</span>
                      <span>${dish.price}</span>
                    </li>
                  ) : null
                })}
              </ul>
              <div className="flex justify-between items-center font-bold">
                <span>Total:</span>
                <span>${calculateBill(selectedCustomer)}</span>
              </div>
              <Button onClick={() => {
                // Here you would typically integrate with a printing service
                console.log('Printing bill for', selectedCustomer.name)
                setIsBillDialogOpen(false)
              }}>
                Print Bill
              </Button>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </div>
  )
}