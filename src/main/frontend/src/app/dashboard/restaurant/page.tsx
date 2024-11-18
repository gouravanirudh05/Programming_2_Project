'use client'

import { useState } from 'react'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import DishManagement from './dish-management'
import TableManagement from './table-management'
import OrderManagement from './order-management'

export type Dish = {
  dishID: string
  name: string
  price: number
  dishType: string
}

export type Table = {
  tableID: string
  capacity: number
  occupied: boolean
  occupiedBy: RestaurantCustomer | null
  orderStatus: 'Vacant' | 'Occupied' | 'Preparing'
}

export type RestaurantCustomer = {
  customerID: string
  name: string
  email: string
  phone: string
  table: Table
  server: string
  dishes: Dish[]
}

export default function RestaurantManagement() {
  const [dishes, setDishes] = useState<Dish[]>([
    { dishID: 'D1', name: 'Margherita Pizza', price: 12.99, dishType: 'Main Course' },
    { dishID: 'D2', name: 'Caesar Salad', price: 8.99, dishType: 'Appetizer' },
    { dishID: 'D3', name: 'Tiramisu', price: 6.99, dishType: 'Dessert' },
  ])

  const [tables, setTables] = useState<Table[]>([
    { tableID: 'T1', capacity: 4, occupied: false, occupiedBy: null, orderStatus: 'Vacant' },
    { tableID: 'T2', capacity: 2, occupied: true, occupiedBy: null, orderStatus: 'Occupied' },
    { tableID: 'T3', capacity: 6, occupied: false, occupiedBy: null, orderStatus: 'Vacant' },
  ])

  const [customers, setCustomers] = useState<RestaurantCustomer[]>([])

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold tracking-tight">Restaurant Management</h1>
      <Tabs defaultValue="dishes">
        <TabsList>
          <TabsTrigger value="dishes">Dishes</TabsTrigger>
          <TabsTrigger value="tables">Tables</TabsTrigger>
          <TabsTrigger value="orders">Orders</TabsTrigger>
        </TabsList>
        <TabsContent value="dishes">
          <Card>
            <CardHeader>
              <CardTitle>Dish Management</CardTitle>
            </CardHeader>
            <CardContent>
              <DishManagement dishes={dishes} setDishes={setDishes} />
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="tables">
          <Card>
            <CardHeader>
              <CardTitle>Table Management</CardTitle>
            </CardHeader>
            <CardContent>
              <TableManagement tables={tables} setTables={setTables} />
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="orders">
          <Card>
            <CardHeader>
              <CardTitle>Order Management</CardTitle>
            </CardHeader>
            <CardContent>
              <OrderManagement 
                tables={tables} 
                setTables={setTables} 
                dishes={dishes} 
                customers={customers} 
                setCustomers={setCustomers} 
              />
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}