"use client"
import React, { useState, useEffect } from 'react'
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

// Define types
type Room = {
  roomId: string
  roomTypeId: string
  capacity: number
  housekeepingLast: string
}

type RoomType = {
  roomTypeId: string
  roomTypeName: string
  tariff: number
  amenities: string[]
}

const API_BASE_URL = 'http://localhost:8080/api'

export default function RoomsManagement() {
  const [rooms, setRooms] = useState<Room[]>([])
  const [roomTypes, setRoomTypes] = useState<RoomType[]>([])
  const [newRoom, setNewRoom] = useState<Room>({
    roomId: '',
    roomTypeId: '',
    capacity: 0,
    housekeepingLast: new Date().toISOString()
  })
  const [newRoomType, setNewRoomType] = useState<RoomType>({
    roomTypeId: '',
    roomTypeName: '',
    tariff: 0,
    amenities: []
  })
  const [isAddRoomOpen, setIsAddRoomOpen] = useState(false)
  const [isAddRoomTypeOpen, setIsAddRoomTypeOpen] = useState(false)
  const [editingRoom, setEditingRoom] = useState<Room | null>(null)
  const [editingRoomType, setEditingRoomType] = useState<RoomType | null>(null)

  // Fetch initial data
  useEffect(() => {
    fetchRooms()
    fetchRoomTypes()
  }, [])

  // API calls for Rooms
  const fetchRooms = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/room/list`)
      for (let i = 0; i < response.data.length; i++) {
        response.data[i].housekeepingLast = new Date(response.data[i].housekeepingLast.dateString+" "+response.data[i].housekeepingLast.timeString).toISOString()
      }
      setRooms(response.data)
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to fetch rooms",
        variant: "destructive"
      })
    }
  }

  const addRoom = async () => {
    try {
      await axios.post(`${API_BASE_URL}/room/add`, newRoom)
      await fetchRooms()
      setNewRoom({
        roomId: '',
        roomTypeId: '',
        capacity: 0,
        housekeepingLast: new Date().toISOString()
      })
      setIsAddRoomOpen(false)
      toast({
        title: "Success",
        description: "Room added successfully"
      })
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to add room",
        variant: "destructive"
      })
    }
  }

  const updateRoom = async () => {
    if (!editingRoom) return
    try {
      await axios.post(`${API_BASE_URL}/room/update/${editingRoom.roomId}`, editingRoom)
      await fetchRooms()
      setEditingRoom(null)
      toast({
        title: "Success",
        description: "Room updated successfully"
      })
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to update room",
        variant: "destructive"
      })
    }
  }

  const deleteRoom = async (roomId: string) => {
    try {
      await axios.post(`${API_BASE_URL}/room/remove/${roomId}`)
      await fetchRooms()
      toast({
        title: "Success",
        description: "Room deleted successfully"
      })
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to delete room",
        variant: "destructive"
      })
    }
  }

  // API calls for Room Types
  const fetchRoomTypes = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/roomtype/list`)
      setRoomTypes(response.data)
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to fetch room types",
        variant: "destructive"
      })
    }
  }

  const addRoomType = async () => {
    try {
      await axios.post(`${API_BASE_URL}/roomtype/add`, newRoomType)
      await fetchRoomTypes()
      setNewRoomType({
        roomTypeId: '',
        roomTypeName: '',
        tariff: 0,
        amenities: []
      })
      setIsAddRoomTypeOpen(false)
      toast({
        title: "Success",
        description: "Room type added successfully"
      })
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to add room type",
        variant: "destructive"
      })
    }
  }

  const updateRoomType = async () => {
    if (!editingRoomType) return
    try {
      await axios.post(`${API_BASE_URL}/roomtype/update/${editingRoomType.roomTypeId}`, editingRoomType)
      await fetchRoomTypes()
      setEditingRoomType(null)
      toast({
        title: "Success",
        description: "Room type updated successfully"
      })
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to update room type",
        variant: "destructive"
      })
    }
  }

  const deleteRoomType = async (roomTypeId: string) => {
    try {
      await axios.post(`${API_BASE_URL}/roomtype/remove/${roomTypeId}`)
      await fetchRoomTypes()
      toast({
        title: "Success",
        description: "Room type deleted successfully"
      })
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to delete room type",
        variant: "destructive"
      })
    }
  }

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold tracking-tight">Room Management</h1>
      <Tabs defaultValue="rooms">
        <TabsList>
          <TabsTrigger value="rooms">Rooms</TabsTrigger>
          <TabsTrigger value="roomTypes">Room Types</TabsTrigger>
        </TabsList>
        <TabsContent value="rooms">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between">
              <CardTitle>Rooms</CardTitle>
              <Dialog open={isAddRoomOpen} onOpenChange={setIsAddRoomOpen}>
                <DialogTrigger asChild>
                  <Button>Add Room</Button>
                </DialogTrigger>
                <DialogContent>
                  <DialogHeader>
                    <DialogTitle>Add New Room</DialogTitle>
                    <DialogDescription>Enter the details for the new room.</DialogDescription>
                  </DialogHeader>
                  <div className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="roomId" className="text-right">Room ID</Label>
                      <Input
                        id="roomId"
                        value={newRoom.roomId}
                        onChange={(e) => setNewRoom({ ...newRoom, roomId: e.target.value })}
                        className="col-span-3"
                      />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="roomTypeId" className="text-right">Room Type</Label>
                      <Select onValueChange={(value) => setNewRoom({ ...newRoom, roomTypeId: value })}>
                        <SelectTrigger className="col-span-3">
                          <SelectValue placeholder="Select room type" />
                        </SelectTrigger>
                        <SelectContent>
                          {roomTypes.map((rt) => (
                            <SelectItem key={rt.roomTypeId} value={rt.roomTypeId}>
                              {rt.roomTypeName}
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="capacity" className="text-right">Capacity</Label>
                      <Input
                        id="capacity"
                        type="number"
                        value={newRoom.capacity}
                        onChange={(e) => setNewRoom({ ...newRoom, capacity: parseInt(e.target.value) })}
                        className="col-span-3"
                      />
                    </div>
                  </div>
                  <DialogFooter>
                    <Button onClick={addRoom}>Add Room</Button>
                  </DialogFooter>
                </DialogContent>
              </Dialog>
            </CardHeader>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Room ID</TableHead>
                    <TableHead>Type</TableHead>
                    <TableHead>Capacity</TableHead>
                    <TableHead>Last Housekeeping</TableHead>
                    <TableHead>Actions</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {rooms.map((room) => (
                    <TableRow key={room.roomId}>
                      <TableCell>{room.roomId}</TableCell>
                      <TableCell>
                        {roomTypes.find(rt => rt.roomTypeId === room.roomTypeId)?.roomTypeName}
                      </TableCell>
                      <TableCell>{room.capacity}</TableCell>
                      <TableCell>{new Date(room.housekeepingLast).toLocaleString()}</TableCell>
                      <TableCell>
                        <Button
                          variant="outline"
                          size="sm"
                          className="mr-2"
                          onClick={() => setEditingRoom(room)}
                        >
                          Edit
                        </Button>
                        <Button
                          variant="destructive"
                          size="sm"
                          onClick={() => deleteRoom(room.roomId)}
                        >
                          Delete
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="roomTypes">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between">
              <CardTitle>Room Types</CardTitle>
              <Dialog open={isAddRoomTypeOpen} onOpenChange={setIsAddRoomTypeOpen}>
                <DialogTrigger asChild>
                  <Button>Add Room Type</Button>
                </DialogTrigger>
                <DialogContent>
                  <DialogHeader>
                    <DialogTitle>Add New Room Type</DialogTitle>
                    <DialogDescription>Enter the details for the new room type.</DialogDescription>
                  </DialogHeader>
                  <div className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="roomTypeId" className="text-right">Room Type ID</Label>
                      <Input
                        id="roomTypeId"
                        value={newRoomType.roomTypeId}
                        onChange={(e) => setNewRoomType({ ...newRoomType, roomTypeId: e.target.value })}
                        className="col-span-3"
                      />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="roomTypeName" className="text-right">Name</Label>
                      <Input
                        id="roomTypeName"
                        value={newRoomType.roomTypeName}
                        onChange={(e) => setNewRoomType({ ...newRoomType, roomTypeName: e.target.value })}
                        className="col-span-3"
                      />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="tariff" className="text-right">Tariff</Label>
                      <Input
                        id="tariff"
                        type="number"
                        value={newRoomType.tariff}
                        onChange={(e) => setNewRoomType({ ...newRoomType, tariff: parseFloat(e.target.value) })}
                        className="col-span-3"
                      />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="amenities" className="text-right">Amenities</Label>
                      <Input
                        id="amenities"
                        value={newRoomType.amenities.join(', ')}
                        onChange={(e) => setNewRoomType({
                          ...newRoomType,
                          amenities: e.target.value.split(',').map(item => item.trim())
                        })}
                        className="col-span-3"
                        placeholder="Enter amenities separated by commas"
                      />
                    </div>
                  </div>
                  <DialogFooter>
                    <Button onClick={addRoomType}>Add Room Type</Button>
                  </DialogFooter>
                </DialogContent>
              </Dialog>
            </CardHeader>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Room Type ID</TableHead>
                    <TableHead>Name</TableHead>
                    <TableHead>Tariff</TableHead>
                    <TableHead>Amenities</TableHead>
                    <TableHead>Actions</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {roomTypes.map((roomType) => (
                    <TableRow key={roomType.roomTypeId}>
                      <TableCell>{roomType.roomTypeId}</TableCell>
                      <TableCell>{roomType.roomTypeName}</TableCell>
                      <TableCell>${roomType.tariff}</TableCell>
                      <TableCell>{roomType.amenities.join(', ')}</TableCell>
                      <TableCell>
                        <Button
                          variant="outline"
                          size="sm"
                          className="mr-2"
                          onClick={() => setEditingRoomType(roomType)}
                        >
                          Edit
                        </Button>
                        <Button
                          variant="destructive"
                          size="sm"
                          onClick={() => deleteRoomType(roomType.roomTypeId)}
                        >
                          Delete
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>

      {/* Edit Room Dialog */}
      {/* Edit Room Dialog */}
      {editingRoom && (
        <Dialog open={!!editingRoom} onOpenChange={() => setEditingRoom(null)}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit Room</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-roomId" className="text-right">Room ID</Label>
                <Input
                  id="edit-roomId"
                  value={editingRoom.roomId}
                  onChange={(e) => setEditingRoom({ ...editingRoom, roomId: e.target.value })}
                  className="col-span-3"
                  disabled
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-roomType" className="text-right">Room Type</Label>
                <Select 
                  value={editingRoom.roomTypeId}
                  onValueChange={(value) => setEditingRoom({ ...editingRoom, roomTypeId: value })}
                >
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select room type" />
                  </SelectTrigger>
                  <SelectContent>
                    {roomTypes.map((rt) => (
                      <SelectItem key={rt.roomTypeId} value={rt.roomTypeId}>
                        {rt.roomTypeName}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-capacity" className="text-right">Capacity</Label>
                <Input
                  id="edit-capacity"
                  type="number"
                  value={editingRoom.capacity}
                  onChange={(e) => setEditingRoom({ ...editingRoom, capacity: parseInt(e.target.value) })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-housekeeping" className="text-right">Last Housekeeping</Label>
                <Input
                  id="edit-housekeeping"
                  type="datetime-local"
                  value={editingRoom.housekeepingLast.slice(0, 16)}
                  onChange={(e) => setEditingRoom({
                    ...editingRoom,
                    housekeepingLast: new Date(e.target.value).toISOString()
                  })}
                  className="col-span-3"
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={updateRoom}>Update Room</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}

      {/* Edit Room Type Dialog */}
      {editingRoomType && (
        <Dialog open={!!editingRoomType} onOpenChange={() => setEditingRoomType(null)}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit Room Type</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-roomTypeId" className="text-right">Room Type ID</Label>
                <Input
                  id="edit-roomTypeId"
                  value={editingRoomType.roomTypeId}
                  onChange={(e) => setEditingRoomType({ ...editingRoomType, roomTypeId: e.target.value })}
                  className="col-span-3"
                  disabled
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-roomTypeName" className="text-right">Name</Label>
                <Input
                  id="edit-roomTypeName"
                  value={editingRoomType.roomTypeName}
                  onChange={(e) => setEditingRoomType({ ...editingRoomType, roomTypeName: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-tariff" className="text-right">Tariff</Label>
                <Input
                  id="edit-tariff"
                  type="number"
                  value={editingRoomType.tariff}
                  onChange={(e) => setEditingRoomType({ ...editingRoomType, tariff: parseFloat(e.target.value) })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-amenities" className="text-right">Amenities</Label>
                <Input
                  id="edit-amenities"
                  value={editingRoomType.amenities.join(', ')}
                  onChange={(e) => setEditingRoomType({
                    ...editingRoomType,
                    amenities: e.target.value.split(',').map(item => item.trim())
                  })}
                  className="col-span-3"
                  placeholder="Enter amenities separated by commas"
                />
              </div>
            </div>
            <DialogFooter>
              <Button onClick={updateRoomType}>Update Room Type</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  )
}