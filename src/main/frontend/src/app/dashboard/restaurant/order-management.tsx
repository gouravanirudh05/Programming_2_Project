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
  const [isEditOrderDialogOpen, setIsEditOrderDialogOpen] = useState(false)
  const [editingCustomer, setEditingCustomer] = useState<RestaurantCustomer | null>(null)

  useEffect(() => {
    fetchCustomers()
    fetchDishes()
    fetchTables()
  }, [])

  const fetchCustomers = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/resturantcustomer/list')
      for (let i = 0; i < response.data.length; i++) {
        response.data[i].id = response.data[i].customerId
        response.data[i].reservedFrom = new Date(response.data[i].reservedFrom.dateString + " " + response.data[i].reservedFrom.timeString).toISOString()
        response.data[i].reservedTo = new Date(response.data[i].reservedTo.dateString + " " + response.data[i].reservedTo.timeString).toISOString()
      }
      setCustomers(response.data.filter((customer: RestaurantCustomer) => customer.tableId !== -1))
    } catch (error) {
      console.error('Error fetching customers:', error)
    }
  }

  const fetchDishes = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/dish/all')
      for (let i = 0; i < response.data.length; i++) {
        response.data[i].id = response.data[i].dishID
      }
      setDishes(response.data)
    } catch (error) {
      console.error('Error fetching dishes:', error)
    }
  }

  const fetchTables = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/table/all')
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
      await axios.post('http://localhost:8080/api/resturantcustomer/add', customerData)
      await axios.post(`http://localhost:8080/api/table/occupy/${availableTable.tableNumber}`)
      fetchCustomers()
      fetchTables()
      setIsNewCustomerDialogOpen(false)
      setNewCustomer({})
    } catch (error) {
      console.error('Error creating new customer:', error)
    }
  }

  const updateOrder = async () => {
    if (!selectedCustomer) return
    try {
      await axios.post(`http://localhost:8080/api/resturantcustomer/update/${selectedCustomer.id}`, editingCustomer)
      // setSelectedCustomer(editingCustomer)
      fetchCustomers()
    } catch (error) {
      console.error('Error adding dish:', error)
    }
  }

  const handleRemoveDish = async (dishId: string) => {
    if (!selectedCustomer) return
    try {
      setEditingCustomer({
        ...editingCustomer,
        dishes: editingCustomer.dishes.filter(id => id !== dishId)
      })
      updateOrder()
    } catch (error) {
      console.error('Error removing dish:', error)
    }
  }

  const handleClearTable = async (customerId: string, tableId: number) => {
    try {
      await axios.post(`http://localhost:8080/api/resturantcustomer/cleartable/${customerId}`, { tableId: -1 })
      await axios.put(`http://localhost:8080/api/tables/${tableId}`, { isOccupied: false })
      fetchCustomers()
      fetchTables()
    } catch (error) {
      console.error('Error clearing table:', error)
    }
  }
  const generateBill = async (customer: RestaurantCustomer) => {
    try {
      let bill = {payedOn: new Date().toISOString(), payed: true, customerID: customer.id, items: []}
      for (let i = 0; i < customer.dishes.length; i++) {
        bill.items.push({name: dishes.find(d => d.id === customer.dishes[i]).name, price: dishes.find(d => d.id === customer.dishes[i]).price, quantity: 1})
      }
      const response = await axios.post(`http://localhost:8080/api/bill/add`, bill)
      console.log(response.data)
    } catch (error) {
      console.error('Error generating bill:', error)
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
                      setEditingCustomer(customer)
                      setIsEditOrderDialogOpen(true)
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
      <Dialog open={isEditOrderDialogOpen} onOpenChange={setIsEditOrderDialogOpen}>
        <DialogContent className="max-w-3xl">
          <DialogHeader>
            <DialogTitle>Edit Order</DialogTitle>
            <DialogDescription>Update the order details.</DialogDescription>
          </DialogHeader>
          {editingCustomer && (
            <div className="grid gap-4 py-4">
              <div>
                <strong>Customer:</strong> {editingCustomer.name}
              </div>
              <div>
                <strong>Table:</strong> {editingCustomer.tableId}
              </div>
              <div>
                <strong>Current Order:</strong>
                <ul className="list-disc pl-5 mt-2">
                  {editingCustomer.dishes.map((dish, index) => (
                    <li key={index} className="flex items-center justify-between">
                      <span>{dishes.find(d => d.id === dish)!.name}</span>
                      <Button 
                        variant="ghost" 
                        size="sm" 
                        onClick={() => setEditingCustomer({
                          ...editingCustomer,
                          dishes: editingCustomer.dishes.filter((_, i) => i !== index)
                        })}
                      >
                        Remove
                      </Button>
                    </li>
                  ))}
                </ul>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="add-dish" className="text-right">Add Dish</Label>
                <Select onValueChange={(value) => setEditingCustomer({
                  ...editingCustomer,
                  dishes: [...editingCustomer.dishes, dishes.find(d => d.id === value).id]
                })}>
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Add dish" />
                  </SelectTrigger>
                  <SelectContent>
                    {dishes.map((dish) => (
                      <SelectItem key={dish.id} value={dish.id}>
                        {dish.name} (${dish.price.toFixed(2)})
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
            </div>
          )}
          <DialogFooter>
            <Button onClick={()=>{updateOrder()}}>Update Order</Button>
          </DialogFooter>
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
                {selectedCustomer.dishes.map((dishId,index) => {
                  const dish = dishes.find(d => d.id === dishId)
                  return dish ? (
                    <li key={index} className="flex justify-between items-center">
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
                generateBill(selectedCustomer)
              }}>
                Generate BILL
              </Button>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </div>
  )
}