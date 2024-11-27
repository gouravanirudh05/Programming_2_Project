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
import { format } from 'date-fns'

type Reservation = {
  reservationID: string
  roomId: string
  customerID: string
  startDateTime: string
  endDateTime: string
}

type Room = {
  roomId: string
  type: string
  capacity: number
  price: number
}
type RoomType = {
  roomTypeId: string
  roomTypeName: string
  tariff: number
  amenities: string[]
}
type Customer = {
  customerID: string
  name: string
  address: string
  phone: string
  email: string
}

export default function ReservationManagement() {
  const [reservations, setReservations] = useState<Reservation[]>([])
  const [rooms, setRooms] = useState<Room[]>([])
  const [customers, setCustomers] = useState<Customer[]>([])
  const [isNewReservationOpen, setIsNewReservationOpen] = useState(false)
  const [currentStep, setCurrentStep] = useState(1)
  const [newReservation, setNewReservation] = useState<Partial<Reservation>>({})
  const [newCustomer, setNewCustomer] = useState<Partial<Customer>>({})
  const [roomType, setRoomType] = useState('')
  const [availableRooms, setAvailableRooms] = useState<Room[]>([])
  const [isExistingCustomer, setIsExistingCustomer] = useState(true)
  const [roomTypes, setRoomTypes] = useState<RoomType[]>([])
  useEffect(() => {
    fetchRoomTypes()
    fetchReservations()
    // fetchRooms()
    // fetchCustomers()
  }, [])

  const fetchReservations = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/reservation/all')
      setReservations(response.data)
    } catch (error) {
      console.error('Error fetching reservations:', error)
    }
  }
  const fetchRoomTypes = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/roomtype/list')
      setRoomTypes(response.data)
    } catch (error) {
      console.error('Error fetching room types:', error)
    }
  }
  const fetchRooms = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/reservation/available',{roomType: roomType, startDateTime: new Date(newReservation.startDateTime).toISOString(), endDateTime: new Date(newReservation.endDateTime).toISOString()})
      setRooms(response.data)
    } catch (error) {
      console.error('Error fetching rooms:', error)
    }
  }

  const fetchCustomers = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/customers')
      setCustomers(response.data)
    } catch (error) {
      console.error('Error fetching customers:', error)
    }
  }

  const handleNewReservation = async () => {
    try {
      if (isExistingCustomer) {
        await axios.post('http://localhost:8080/api/reservation/add', newReservation)
      } else {
        const customerResponse = await axios.post('http://localhost:8080/api/hotelcustomer/add', newCustomer)
        const customerID = customerResponse.data.customerId
        await axios.post('http://localhost:8080/api/reservation/add', { ...newReservation, customerID })
      }
      fetchReservations()
      setIsNewReservationOpen(false)
      resetNewReservationForm()
    } catch (error) {
      console.error('Error creating reservation:', error)
    }
  }

  const resetNewReservationForm = () => {
    setNewReservation({})
    setNewCustomer({})
    setCurrentStep(1)
    setRoomType('')
    setAvailableRooms([])
    setIsExistingCustomer(true)
  }

  const handleNextStep = async () => {
    if (currentStep === 1) {
      try {
        const response = await axios.post('http://localhost:8080/api/reservation/available', {
            roomType: roomType,
            startDateTime: newReservation.startDateTime,
            endDateTime: newReservation.endDateTime
        })
        setAvailableRooms(response.data)
        setCurrentStep(2)
      } catch (error) {
        console.error('Error fetching available rooms:', error)
      }
    } else if (currentStep === 2) {
      setCurrentStep(3)
    }
  }

  const renderReservationStep = () => {
    switch (currentStep) {
      case 1:
        return (
          <>
            <div className="grid w-full items-center gap-4">
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="roomType">Room Type</Label>
                <Select onValueChange={(value) => setRoomType(value)}>
                  <SelectTrigger id="roomType">
                    <SelectValue placeholder="Select room type" />
                  </SelectTrigger>
                  <SelectContent>
                    {roomTypes.map((roomType) => (
                      <SelectItem key={roomType.roomTypeId} value={roomType.roomTypeId}>
                        {roomType.roomTypeName} (${roomType.tariff})
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="startDateTime">Check-in Date</Label>
                <Input
                  id="startDateTime"
                  type="datetime-local"
                  value={newReservation.startDateTime || ''}
                  onChange={(e) => setNewReservation({ ...newReservation, startDateTime: e.target.value })}
                />
              </div>
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="endDateTime">Check-out Date</Label>
                <Input
                  id="endDateTime"
                  type="datetime-local"
                  value={newReservation.endDateTime || ''}
                  onChange={(e) => setNewReservation({ ...newReservation, endDateTime: e.target.value })}
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={handleNextStep}>Next</Button>
            </DialogFooter>
          </>
        )
      case 2:
        return (
          <>
            <div className="grid w-full items-center gap-4">
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="roomId">Available Rooms</Label>
                <Select onValueChange={(value) => setNewReservation({ ...newReservation, roomId: value })}>
                  <SelectTrigger id="roomId">
                    <SelectValue placeholder="Select a room" />
                  </SelectTrigger>
                  <SelectContent>
                    {availableRooms.map((room) => (
                      <SelectItem key={room.roomId} value={room.roomId}>
                        {room.roomId} - {room.type} (Capacity: {room.capacity})
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
            </div>
            <DialogFooter>
              <Button onClick={handleNextStep}>Next</Button>
            </DialogFooter>
          </>
        )
      case 3:
        return (
          <>
            <div className="grid w-full items-center gap-4">
              <div className="flex flex-col space-y-1.5">
                <Label>Customer Type</Label>
                <Select onValueChange={(value) => setIsExistingCustomer(value === 'existing')}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select customer type" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="existing">Existing Customer</SelectItem>
                    <SelectItem value="new">New Customer</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              {isExistingCustomer ? (
                <div className="flex flex-col space-y-1.5">
                  <Label htmlFor="customerID">Customer</Label>
                  <Select onValueChange={(value) => setNewReservation({ ...newReservation, customerID: value })}>
                    <SelectTrigger id="customerID">
                      <SelectValue placeholder="Select a customer" />
                    </SelectTrigger>
                    <SelectContent>
                      {customers.map((customer) => (
                        <SelectItem key={customer.customerID} value={customer.customerID}>
                          {customer.name} ({customer.email})
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              ) : (
                <>
                  <div className="flex flex-col space-y-1.5">
                    <Label htmlFor="name">Name</Label>
                    <Input
                      id="name"
                      value={newCustomer.name || ''}
                      onChange={(e) => setNewCustomer({ ...newCustomer, name: e.target.value })}
                    />
                  </div>
                  <div className="flex flex-col space-y-1.5">
                    <Label htmlFor="address">Address</Label>
                    <Input
                      id="address"
                      value={newCustomer.address || ''}
                      onChange={(e) => setNewCustomer({ ...newCustomer, address: e.target.value })}
                    />
                  </div>
                  <div className="flex flex-col space-y-1.5">
                    <Label htmlFor="phone">Phone</Label>
                    <Input
                      id="phone"
                      value={newCustomer.phone || ''}
                      onChange={(e) => setNewCustomer({ ...newCustomer, phone: e.target.value })}
                    />
                  </div>
                  <div className="flex flex-col space-y-1.5">
                    <Label htmlFor="email">Email</Label>
                    <Input
                      id="email"
                      type="email"
                      value={newCustomer.email || ''}
                      onChange={(e) => setNewCustomer({ ...newCustomer, email: e.target.value })}
                    />
                  </div>
                </>
              )}
            </div>
            <DialogFooter>
              <Button onClick={handleNewReservation}>Create Reservation</Button>
            </DialogFooter>
          </>
        )
      default:
        return null
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold tracking-tight">Reservation Management</h1>
        <Dialog open={isNewReservationOpen} onOpenChange={setIsNewReservationOpen}>
          <DialogTrigger asChild>
            <Button onClick={() => setIsNewReservationOpen(true)}>New Reservation</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Create New Reservation</DialogTitle>
              <DialogDescription>Fill in the details for the new reservation.</DialogDescription>
            </DialogHeader>
            {renderReservationStep()}
          </DialogContent>
        </Dialog>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>All Reservations</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Reservation ID</TableHead>
                <TableHead>Room ID</TableHead>
                <TableHead>Customer ID</TableHead>
                <TableHead>Check-in</TableHead>
                <TableHead>Check-out</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {reservations.map((reservation) => (
                <TableRow key={reservation.reservationID}>
                  <TableCell>{reservation.reservationID}</TableCell>
                  <TableCell>{reservation.roomId}</TableCell>
                  <TableCell>{reservation.customerID}</TableCell>
                  <TableCell>{format(new Date(reservation.startDateTime), 'PPpp')}</TableCell>
                  <TableCell>{format(new Date(reservation.endDateTime), 'PPpp')}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  )
}