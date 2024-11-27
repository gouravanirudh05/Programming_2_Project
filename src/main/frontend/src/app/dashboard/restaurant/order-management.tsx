// 'use client'

// import { useState } from 'react'
// import { Button } from '@/components/ui/button'
// import { Input } from '@/components/ui/input'
// import { Label } from '@/components/ui/label'
// import {
//   Table,
//   TableBody,
//   TableCell,
//   TableHead,
//   TableHeader,
//   TableRow,
// } from '@/components/ui/table'
// import {
//   Dialog,
//   DialogContent,
//   DialogDescription,
//   DialogFooter,
//   DialogHeader,
//   DialogTitle,
//   DialogTrigger,
// } from '@/components/ui/dialog'
// import {
//   Select,
//   SelectContent,
//   SelectItem,
//   SelectTrigger,
//   SelectValue,
// } from '@/components/ui/select'
// import { Table as TableType, Dish, RestaurantCustomer } from './page'


// export default function OrderManagement() {
//   const [selectedTable, setSelectedTable] = useState<TableType | null>(null)
//   const [selectedDishes, setSelectedDishes] = useState<Dish[]>([])
//   const [newCustomer, setNewCustomer] = useState<Omit<RestaurantCustomer, 'table' | 'dishes'>>({
//     customerID: '',
//     name: '',
//     email: '',
//     phone: '',
//     server: ''
//   })
//   const [isEditOrderDialogOpen, setIsEditOrderDialogOpen] = useState(false)
//   const [editingCustomer, setEditingCustomer] = useState<RestaurantCustomer | null>(null)

//   const createOrder = () => {
//     if (selectedTable && newCustomer.name) {
//       const customer: RestaurantCustomer = {
//         ...newCustomer,
//         customerID: `RC${customers.length + 1}`,
//         table: selectedTable,
//         dishes: selectedDishes
//       }
//       setCustomers([...customers, customer])
//       setTables(tables.map(table => 
//         table.tableID === selectedTable.tableID 
//           ? { ...table, occupied: true, occupiedBy: customer, orderStatus: 'Occupied' }
//           : table
//       ))
//       setSelectedTable(null)
//       setSelectedDishes([])
//       setNewCustomer({ customerID: '', name: '', email: '', phone: '', server: '' })
//     }
//   }

//   const updateOrder = () => {
//     if (editingCustomer) {
//       const updatedCustomers = customers.map(customer => 
//         customer.customerID === editingCustomer.customerID ? editingCustomer : customer
//       )
//       setCustomers(updatedCustomers)
//       setTables(tables.map(table => 
//         table.tableID === editingCustomer.table.tableID 
//           ? { ...table, occupiedBy: editingCustomer }
//           : table
//       ))
//       setEditingCustomer(null)
//       setIsEditOrderDialogOpen(false)
//     }
//   }

//   const generateBill = (customer: RestaurantCustomer) => {
//     const total = customer.dishes.reduce((sum, dish) => sum + dish.price, 0)
//     alert(`Bill for ${customer.name}:\n\n${customer.dishes.map(dish => `${dish.name}: $${dish.price.toFixed(2)}`).join('\n')}\n\nTotal: $${total.toFixed(2)}`)
//   }

//   return (
//     <div>
//       <div className="mb-4 space-x-2">
//         <Dialog>
//           <DialogTrigger asChild>
//             <Button>Create Order</Button>
//           </DialogTrigger>
//           <DialogContent className="max-w-3xl">
//             <DialogHeader>
//               <DialogTitle>Create New Order</DialogTitle>
//               <DialogDescription>Select a table and add dishes to the order.</DialogDescription>
//             </DialogHeader>
//             <div className="grid gap-4 py-4">
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="table" className="text-right">Table</Label>
//                 <Select onValueChange={(value) => setSelectedTable(tables.find(t => t.tableID === value) || null)}>
//                   <SelectTrigger className="col-span-3">
//                     <SelectValue placeholder="Select table" />
//                   </SelectTrigger>
//                   <SelectContent>
//                     {tables.filter(t => !t.occupied).map((table) => (
//                       <SelectItem key={table.tableID} value={table.tableID}>
//                         {table.tableID} (Capacity: {table.capacity})
//                       </SelectItem>
//                     ))}
//                   </SelectContent>
//                 </Select>
//               </div>
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="customerName" className="text-right">Customer Name</Label>
//                 <Input
//                   id="customerName"
//                   value={newCustomer.name}
//                   onChange={(e) => setNewCustomer({ ...newCustomer, name: e.target.value })}
//                   className="col-span-3"
//                 />
//               </div>
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="customerPhone" className="text-right">Customer Phone</Label>
//                 <Input
//                   id="customerPhone"
//                   value={newCustomer.phone}
//                   onChange={(e) => setNewCustomer({ ...newCustomer, phone: e.target.value })}
//                   className="col-span-3"
//                 />
//               </div>
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="server" className="text-right">Server</Label>
//                 <Input
//                   id="server"
//                   value={newCustomer.server}
//                   onChange={(e) => setNewCustomer({ ...newCustomer, server: e.target.value })}
//                   className="col-span-3"
//                 />
//               </div>
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="dishes" className="text-right">Dishes</Label>
//                 <Select onValueChange={(value) => setSelectedDishes([...selectedDishes, dishes.find(d => d.dishID === Number(value))!])}>
//                   <SelectTrigger className="col-span-3">
//                     <SelectValue placeholder="Add dish" />
//                   </SelectTrigger>
//                   <SelectContent>
//                     {dishes.map((dish) => (
//                       <SelectItem key={dish.dishID} value={dish.dishID.toString()}>
//                         {dish.name} (${dish.price.toFixed(2)})
//                       </SelectItem>
//                     ))}
//                   </SelectContent>
//                 </Select>
//               </div>
//               {selectedDishes.length > 0 && (
//                 <div className="col-span-4">
//                   <h4 className="mb-2 font-semibold">Selected Dishes:</h4>
//                   <ul className="list-disc pl-5">
//                     {selectedDishes.map((dish, index) => (
//                       <li key={index}>
//                         {dish.name} - ${dish.price.toFixed(2)}
//                         <Button 
//                           variant="ghost" 
//                           size="sm" 
//                           onClick={() => setSelectedDishes(selectedDishes.filter((_, i) => i !== index))}
//                           className="ml-2"
//                         >
//                           Remove
//                         </Button>
//                       </li>
//                     ))}
//                   </ul>
//                 </div>
//               )}
//             </div>
//             <DialogFooter>
//               <Button onClick={createOrder}>Create Order</Button>
//             </DialogFooter>
//           </DialogContent>
//         </Dialog>
//       </div>
//       <Table>
//         <TableHeader>
//           <TableRow>
//             <TableHead>Table</TableHead>
//             <TableHead>Customer</TableHead>
//             <TableHead>Server</TableHead>
//             <TableHead>Status</TableHead>
//             <TableHead>Dishes</TableHead>
//             <TableHead>Actions</TableHead>
//           </TableRow>
//         </TableHeader>
//         <TableBody>
//           {tables.filter(table => table.occupied).map((table) => (
//             <TableRow key={table.tableID}>
//               <TableCell>{table.tableID}</TableCell>
//               <TableCell>{table.occupiedBy?.name}</TableCell>
//               <TableCell>{table.occupiedBy?.server}</TableCell>
//               <TableCell>{table.orderStatus}</TableCell>
//               <TableCell>
//                 <ul className="list-disc pl-5">
//                   {table.occupiedBy?.dishes.map((dish, index) => (
//                     <li key={index}>{dish.name} - ${dish.price.toFixed(2)}</li>
//                   ))}
//                 </ul>
//               </TableCell>
//               <TableCell>
//                 <Button variant="outline" size="sm" className="mr-2" onClick={() => {
//                   setEditingCustomer(table.occupiedBy!)
//                   setIsEditOrderDialogOpen(true)
//                 }}>
//                   Edit Order
//                 </Button>
//                 <Button variant="outline" size="sm" className="mr-2" onClick={() => generateBill(table.occupiedBy!)}>
//                   Generate Bill
//                 </Button>
//                 <Button 
//                   variant="destructive" 
//                   size="sm"
//                   onClick={() => {
//                     setTables(tables.map(t => 
//                       t.tableID === table.tableID 
//                         ? { ...t, occupied: false, occupiedBy: null, orderStatus: 'Vacant' }
//                         : t
//                     ))
//                     setCustomers(customers.filter(c => c.customerID !== table.occupiedBy?.customerID))
//                   }}
//                 >
//                   Clear Table
//                 </Button>
//               </TableCell>
//             </TableRow>
//           ))}
//         </TableBody>
//       </Table>

//       <Dialog open={isEditOrderDialogOpen} onOpenChange={setIsEditOrderDialogOpen}>
//         <DialogContent className="max-w-3xl">
//           <DialogHeader>
//             <DialogTitle>Edit Order</DialogTitle>
//             <DialogDescription>Update the order details.</DialogDescription>
//           </DialogHeader>
//           {editingCustomer && (
//             <div className="grid gap-4 py-4">
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="edit-customerName" className="text-right">Customer Name</Label>
//                 <Input
//                   id="edit-customerName"
//                   value={editingCustomer.name}
//                   onChange={(e) => setEditingCustomer({ ...editingCustomer, name: e.target.value })}
//                   className="col-span-3"
//                 />
//               </div>
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="edit-customerPhone" className="text-right">Customer Phone</Label>
//                 <Input
//                   id="edit-customerPhone"
//                   value={editingCustomer.phone}
//                   onChange={(e) => setEditingCustomer({ ...editingCustomer, phone: e.target.value })}
//                   className="col-span-3"
//                 />
//               </div>
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="edit-server" className="text-right">Server</Label>
//                 <Input
//                   id="edit-server"
//                   value={editingCustomer.server}
//                   onChange={(e) => setEditingCustomer({ ...editingCustomer, server: e.target.value })}
//                   className="col-span-3"
//                 />
//               </div>
//               <div className="grid grid-cols-4 items-center gap-4">
//                 <Label htmlFor="edit-dishes" className="text-right">Add Dish</Label>
//                 <Select onValueChange={(value) => setEditingCustomer({
//                   ...editingCustomer,
//                   dishes: [...editingCustomer.dishes, dishes.find(d => d.dishID === Number(value))!]
//                 })}>
//                   <SelectTrigger className="col-span-3">
//                     <SelectValue placeholder="Add dish" />
//                   </SelectTrigger>
//                   <SelectContent>
//                     {dishes.map((dish) => (
//                       <SelectItem key={dish.dishID} value={dish.dishID.toString()}>
//                         {dish.name} (${dish.price.toFixed(2)})
//                       </SelectItem>
//                     ))}
//                   </SelectContent>
//                 </Select>
//               </div>
//               <div className="col-span-4">
//                 <h4 className="mb-2 font-semibold">Current Order:</h4>
//                 <ul className="list-disc pl-5">
//                   {editingCustomer.dishes.map((dish, index) => (
//                     <li key={index}>
//                       {dish.name} - ${dish.price.toFixed(2)}
//                       <Button 
//                         variant="ghost" 
//                         size="sm" 
//                         onClick={() => setEditingCustomer({
//                           ...editingCustomer,
//                           dishes: editingCustomer.dishes.filter((_, i) => i !== index)
//                         })}
//                         className="ml-2"
//                       >
//                         Remove
//                       </Button>
//                     </li>
//                   ))}
//                 </ul>
//               </div>
//             </div>
//           )}
//           <DialogFooter>
//             <Button onClick={updateOrder}>Update Order</Button>
//           </DialogFooter>
//         </DialogContent>
//       </Dialog>
//     </div>
//   )
// }
export default function OrderManagement() {
  return (
    <div>
      <h1>Order Management</h1>
    </div>
  )
}