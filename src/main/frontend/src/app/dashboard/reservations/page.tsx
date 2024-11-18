'use client'

import { useState } from 'react'
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

type Reservation = {
  reservationID: string
  room: string
  reservedFrom: string
  reservedTo: string
  tariff: number
  billID: string
  customerID: string
}

type Customer = {
  customerID: string
  name: string
  phone: string
}

type Room = {
  roomID: string
  roomType: string
}

const initialReservations: Reservation[] = [
  {
    reservationID: 'R001',
    room: '101',
    reservedFrom: '2024-01-20',
    reservedTo: '2024-01-22',
    tariff: 200,
    billID: 'B001',
    customerID: 'C001'
  }
]

const initialCustomers: Customer[] = [
  { customerID: 'C001', name: 'John Doe', phone: '123-456-7890' },
  { customerID: 'C002', name: 'Jane Smith', phone: '098-765-4321' }
]

const initialRooms: Room[] = [
  { roomID: '101', roomType: 'Standard' },
  { roomID: '102', roomType: 'Deluxe' },
  { roomID: '103', roomType: 'Suite' }
]

export default function ReservationManagement() {
  const [reservations, setReservations] = useState<Reservation[]>(initialReservations)
  const [newReservation, setNewReservation] = useState<Reservation>({
    reservationID: '',
    room: '',
    reservedFrom: '',
    reservedTo: '',
    tariff: 0,
    billID: '',
    customerID: ''
  })
  const [isAddReservationOpen, setIsAddReservationOpen] = useState(false)
  const [editingReservation, setEditingReservation] = useState<Reservation | null>(null)
  const [customers] = useState<Customer[]>(initialCustomers)
  const [rooms] = useState<Room[]>(initialRooms)
  const [selectedCustomer, setSelectedCustomer] = useState<string>('')
  const [isNewCustomer, setIsNewCustomer] = useState(false)
  const [newCustomer, setNewCustomer] = useState<Customer>({ customerID: '', name: '', phone: '' })

  const addReservation = () => {
    const reservationToAdd = {
      ...newReservation,
      reservationID: `R${reservations.length + 1}`.padStart(4, '0'),
      billID: `B${reservations.length + 1}`.padStart(4, '0'),
      customerID: isNewCustomer ? `C${customers.length + 1}`.padStart(4, '0') : selectedCustomer
    }
    setReservations([...reservations, reservationToAdd])
    setNewReservation({
      reservationID: '',
      room: '',
      reservedFrom: '',
      reservedTo: '',
      tariff: 0,
      billID: '',
      customerID: ''
    })
    setIsAddReservationOpen(false)
  }

  const updateReservation = () => {
    if (editingReservation) {
      setReservations(reservations.map(reservation => 
        reservation.reservationID === editingReservation.reservationID ? editingReservation : reservation
      ))
      setEditingReservation(null)
    }
  }

  const deleteReservation = (reservationID: string) => {
    setReservations(reservations.filter(reservation => reservation.reservationID !== reservationID))
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold tracking-tight">Reservation Management</h1>
        <Dialog open={isAddReservationOpen} onOpenChange={setIsAddReservationOpen}>
          <DialogTrigger asChild>
            <Button>Add Reservation</Button>
          </DialogTrigger>
          <DialogContent className="max-w-md">
            <DialogHeader>
              <DialogTitle>Add New Reservation</DialogTitle>
              <DialogDescription>Enter the details for the new reservation.</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="customer" className="text-right">Customer</Label>
                <Select onValueChange={(value) => {
                  setSelectedCustomer(value)
                  setIsNewCustomer(value === 'new')
                }}>
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select customer" />
                  </SelectTrigger>
                  <SelectContent>
                    {customers.map((customer) => (
                      <SelectItem key={customer.customerID} value={customer.customerID}>
                        {customer.name} ({customer.phone})
                      </SelectItem>
                    ))}
                    <SelectItem value="new">Add New Customer</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              {isNewCustomer && (
                <>
                  <div className="grid grid-cols-4 items-center gap-4">
                    <Label htmlFor="newCustomerName" className="text-right">Name</Label>
                    <Input
                      id="newCustomerName"
                      value={newCustomer.name}
                      onChange={(e) => setNewCustomer({ ...newCustomer, name: e.target.value })}
                      className="col-span-3"
                    />
                  </div>
                  <div className="grid grid-cols-4 items-center gap-4">
                    <Label htmlFor="newCustomerPhone" className="text-right">Phone</Label>
                    <Input
                      id="newCustomerPhone"
                      value={newCustomer.phone}
                      onChange={(e) => setNewCustomer({ ...newCustomer, phone: e.target.value })}
                      className="col-span-3"
                    />
                  </div>
                </>
              )}
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="room" className="text-right">Room</Label>
                <Select onValueChange={(value) => setNewReservation({ ...newReservation, room: value })}>
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select room" />
                  </SelectTrigger>
                  <SelectContent>
                    {rooms.map((room) => (
                      <SelectItem key={room.roomID} value={room.roomID}>
                        {room.roomID} ({room.roomType})
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="reservedFrom" className="text-right">From</Label>
                <Input
                  id="reservedFrom"
                  type="date"
                  value={newReservation.reservedFrom}
                  onChange={(e) => setNewReservation({ ...newReservation, reservedFrom: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="reservedTo" className="text-right">To</Label>
                <Input
                  id="reservedTo"
                  type="date"
                  value={newReservation.reservedTo}
                  onChange={(e) => setNewReservation({ ...newReservation, reservedTo: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="tariff" className="text-right">Tariff</Label>
                <Input
                  id="tariff"
                  type="number"
                  value={newReservation.tariff}
                  onChange={(e) => setNewReservation({ ...newReservation, tariff: parseFloat(e.target.value) })}
                  className="col-span-3"
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={addReservation}>Add Reservation</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>Reservations</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Room</TableHead>
                <TableHead>From</TableHead>
                <TableHead>To</TableHead>
                <TableHead>Tariff</TableHead>
                <TableHead>Bill ID</TableHead>
                <TableHead>Customer ID</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {reservations.map((reservation) => (
                <TableRow key={reservation.reservationID}>
                  <TableCell>{reservation.reservationID}</TableCell>
                  <TableCell>{reservation.room}</TableCell>
                  <TableCell>{reservation.reservedFrom}</TableCell>
                  <TableCell>{reservation.reservedTo}</TableCell>
                  <TableCell>${reservation.tariff}</TableCell>
                  <TableCell>{reservation.billID}</TableCell>
                  <TableCell>{reservation.customerID}</TableCell>
                  <TableCell>
                    <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingReservation(reservation)}>
                      Edit
                    </Button>
                    <Button variant="destructive" size="sm" onClick={() => deleteReservation(reservation.reservationID)}>
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {editingReservation && (
        <Dialog open={!!editingReservation} onOpenChange={() => setEditingReservation(null)}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit Reservation</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-room" className="text-right">Room</Label>
                <Select 
                  value={editingReservation.room}
                  onValueChange={(value) => setEditingReservation({ ...editingReservation, room: value })}
                >
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select room" />
                  </SelectTrigger>
                  <SelectContent>
                    {rooms.map((room) => (
                      <SelectItem key={room.roomID} value={room.roomID}>
                        {room.roomID} ({room.roomType})
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-reservedFrom" className="text-right">From</Label>
                <Input
                  id="edit-reservedFrom"
                  type="date"
                  value={editingReservation.reservedFrom}
                  onChange={(e) => setEditingReservation({ ...editingReservation, reservedFrom: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-reservedTo" className="text-right">To</Label>
                <Input
                  id="edit-reservedTo"
                  type="date"
                  value={editingReservation.reservedTo}
                  onChange={(e) => setEditingReservation({ ...editingReservation, reservedTo: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-tariff" className="text-right">Tariff</Label>
                <Input
                  id="edit-tariff"
                  type="number"
                  value={editingReservation.tariff}
                  onChange={(e) => setEditingReservation({ ...editingReservation, tariff: parseFloat(e.target.value) })}
                  className="col-span-3"
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={updateReservation}>Update Reservation</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  )
}