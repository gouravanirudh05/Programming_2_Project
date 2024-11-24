'use client'

import { useState } from 'react'
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
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'

// Types
type Room = {
  roomID: string
  roomType: string
  capacity: number
  housekeepingLast: string
}

type RoomType = {
  roomTypeID: string
  name: string
  tariff: number
  amenities: string[]
}

// Dummy data
const initialRooms: Room[] = [
  { roomID: 'R101', roomType: 'DELUXE', capacity: 2, housekeepingLast: '2024-01-17T08:00:00' },
  { roomID: 'R102', roomType: 'SUITE', capacity: 4, housekeepingLast: '2024-01-17T09:00:00' },
  { roomID: 'R103', roomType: 'STANDARD', capacity: 2, housekeepingLast: '2024-01-17T10:00:00' },
]

const initialRoomTypes: RoomType[] = [
  { roomTypeID: 'RT1', name: 'STANDARD', tariff: 100, amenities: ['TV', 'Wi-Fi'] },
  { roomTypeID: 'RT2', name: 'DELUXE', tariff: 150, amenities: ['TV', 'Wi-Fi', 'Mini Bar'] },
  { roomTypeID: 'RT3', name: 'SUITE', tariff: 250, amenities: ['TV', 'Wi-Fi', 'Mini Bar', 'Jacuzzi'] },
]
import axios from 'axios';

const API_BASE_URL = '/api/room';

const getRoom = async (roomId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/${roomId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching room:', error.response?.data || error.message);
    throw error;
  }
};

const addRoom = async (roomData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/add`, roomData);
    return response.data;
  } catch (error) {
    console.error('Error adding room:', error.response?.data || error.message);
    throw error;
  }
};

const removeRoom = async (roomId) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/remove/${roomId}`);
    return response.data;
  } catch (error) {
    console.error('Error removing room:', error.response?.data || error.message);
    throw error;
  }
};

const updateRoom = async (roomId, roomData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/update/${roomId}`, roomData);
    return response.data;
  } catch (error) {
    console.error('Error updating room:', error.response?.data || error.message);
    throw error;
  }
};

const getAllRooms = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/list`);
    return response.data;
  } catch (error) {
    console.error('Error fetching rooms:', error.response?.data || error.message);
    throw error;
  }
};

const saveToFile = async () => {
  try {
    const response = await axios.post(`${API_BASE_URL}/save`);
    return response.data;
  } catch (error) {
    console.error('Error saving room data:', error.response?.data || error.message);
    throw error;
  }
};

const loadFromFile = async () => {
  try {
    const response = await axios.post(`${API_BASE_URL}/load`);
    return response.data;
  } catch (error) {
    console.error('Error loading room data:', error.response?.data || error.message);
    throw error;
  }
};

const API_BASE_URL_2 = '/api/roomtype';

const getRoomType = async (roomTypeId) => {
  try {
    const response = await axios.get(`${API_BASE_URL_2}/${roomTypeId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching room type:', error.response?.data || error.message);
    throw error;
  }
};

const addRoomType = async (roomTypeData) => {
  try {
    const response = await axios.post(`${API_BASE_URL_2}/add`, roomTypeData);
    return response.data;
  } catch (error) {
    console.error('Error adding room type:', error.response?.data || error.message);
    throw error;
  }
};

const removeRoomType = async (roomTypeId) => {
  try {
    const response = await axios.post(`${API_BASE_URL_2}/remove/${roomTypeId}`);
    return response.data;
  } catch (error) {
    console.error('Error removing room type:', error.response?.data || error.message);
    throw error;
  }
};

const updateRoomType = async (roomTypeId, roomTypeData) => {
  try {
    const response = await axios.post(`${API_BASE_URL_2}/update/${roomTypeId}`, roomTypeData);
    return response.data;
  } catch (error) {
    console.error('Error updating room type:', error.response?.data || error.message);
    throw error;
  }
};

const getAllRoomTypes = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL_2}/list`);
    return response.data;
  } catch (error) {
    console.error('Error fetching room types:', error.response?.data || error.message);
    throw error;
  }
};

const saveRoomTypesToFile = async () => {
  try {
    const response = await axios.post(`${API_BASE_URL_2}/save`);
    return response.data;
  } catch (error) {
    console.error('Error saving room type data:', error.response?.data || error.message);
    throw error;
  }
};

const loadRoomTypesFromFile = async () => {
  try {
    const response = await axios.post(`${API_BASE_URL_2}/load`);
    return response.data;
  } catch (error) {
    console.error('Error loading room type data:', error.response?.data || error.message);
    throw error;
  }
};


export default function RoomsManagement() {
  const [rooms, setRooms] = useState<Room[]>(initialRooms)
  const [roomTypes, setRoomTypes] = useState<RoomType[]>(initialRoomTypes)
  const [newRoom, setNewRoom] = useState<Room>({ roomID: '', roomType: '', capacity: 0, housekeepingLast: '' })
  const [newRoomType, setNewRoomType] = useState<RoomType>({ roomTypeID: '', name: '', tariff: 0, amenities: [] })
  const [isAddRoomOpen, setIsAddRoomOpen] = useState(false)
  const [isAddRoomTypeOpen, setIsAddRoomTypeOpen] = useState(false)
  const [editingRoom, setEditingRoom] = useState<Room | null>(null)
  const [editingRoomType, setEditingRoomType] = useState<RoomType | null>(null)

  // Room CRUD operations
  const addRoom = () => {
    setRooms([...rooms, newRoom])
    setNewRoom({ roomID: '', roomType: '', capacity: 0, housekeepingLast: '' })
    setIsAddRoomOpen(false)
  }

  const updateRoom = () => {
    if (editingRoom) {
      setRooms(rooms.map(room => room.roomID === editingRoom.roomID ? editingRoom : room))
      setEditingRoom(null)
    }
  }

  const deleteRoom = (roomID: string) => {
    setRooms(rooms.filter(room => room.roomID !== roomID))
  }

  // RoomType CRUD operations
  const addRoomType = () => {
    setRoomTypes([...roomTypes, newRoomType])
    setNewRoomType({ roomTypeID: '', name: '', tariff: 0, amenities: [] })
    setIsAddRoomTypeOpen(false)
  }

  const updateRoomType = () => {
    if (editingRoomType) {
      setRoomTypes(roomTypes.map(rt => rt.roomTypeID === editingRoomType.roomTypeID ? editingRoomType : rt))
      setEditingRoomType(null)
    }
  }

  const deleteRoomType = (roomTypeID: string) => {
    setRoomTypes(roomTypes.filter(rt => rt.roomTypeID !== roomTypeID))
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
                      <Label htmlFor="roomID" className="text-right">Room ID</Label>
                      <Input
                        id="roomID"
                        value={newRoom.roomID}
                        onChange={(e) => setNewRoom({ ...newRoom, roomID: e.target.value })}
                        className="col-span-3"
                      />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="roomType" className="text-right">Room Type</Label>
                      <Select onValueChange={(value) => setNewRoom({ ...newRoom, roomType: value })}>
                        <SelectTrigger className="col-span-3">
                          <SelectValue placeholder="Select room type" />
                        </SelectTrigger>
                        <SelectContent>
                          {roomTypes.map((rt) => (
                            <SelectItem key={rt.roomTypeID} value={rt.name}>{rt.name}</SelectItem>
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
                    <TableRow key={room.roomID}>
                      <TableCell>{room.roomID}</TableCell>
                      <TableCell>{room.roomType}</TableCell>
                      <TableCell>{room.capacity}</TableCell>
                      <TableCell>{new Date(room.housekeepingLast).toLocaleString()}</TableCell>
                      <TableCell>
                        <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingRoom(room)}>
                          Edit
                        </Button>
                        <Button variant="destructive" size="sm" onClick={() => deleteRoom(room.roomID)}>
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
                      <Label htmlFor="roomTypeID" className="text-right">Room Type ID</Label>
                      <Input
                        id="roomTypeID"
                        value={newRoomType.roomTypeID}
                        onChange={(e) => setNewRoomType({ ...newRoomType, roomTypeID: e.target.value })}
                        className="col-span-3"
                      />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="name" className="text-right">Name</Label>
                      <Input
                        id="name"
                        value={newRoomType.name}
                        onChange={(e) => setNewRoomType({ ...newRoomType, name: e.target.value })}
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
                        onChange={(e) => setNewRoomType({ ...newRoomType, amenities: e.target.value.split(', ') })}
                        className="col-span-3"
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
                    <TableRow key={roomType.roomTypeID}>
                      <TableCell>{roomType.roomTypeID}</TableCell>
                      <TableCell>{roomType.name}</TableCell>
                      <TableCell>${roomType.tariff}</TableCell>
                      <TableCell>{roomType.amenities.join(', ')}</TableCell>
                      <TableCell>
                        <Button variant="outline" size="sm" className="mr-2" onClick={() => setEditingRoomType(roomType)}>
                          Edit
                        </Button>
                        <Button variant="destructive" size="sm" onClick={() => deleteRoomType(roomType.roomTypeID)}>
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
      {editingRoom && (
        <Dialog open={!!editingRoom} onOpenChange={() => setEditingRoom(null)}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit Room</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-roomID" className="text-right">Room ID</Label>
                <Input
                  id="edit-roomID"
                  value={editingRoom.roomID}
                  onChange={(e) => setEditingRoom({ ...editingRoom, roomID: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-roomType" className="text-right">Room Type</Label>
                <Select 
                  value={editingRoom.roomType}
                  onValueChange={(value) => setEditingRoom({ ...editingRoom, roomType: value })}
                >
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select room type" />
                  </SelectTrigger>
                  <SelectContent>
                    {roomTypes.map((rt) => (
                      <SelectItem key={rt.roomTypeID} value={rt.name}>{rt.name}</SelectItem>
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
                <Label htmlFor="edit-roomTypeID" className="text-right">Room Type ID</Label>
                <Input
                  id="edit-roomTypeID"
                  value={editingRoomType.roomTypeID}
                  onChange={(e) => setEditingRoomType({ ...editingRoomType, roomTypeID: e.target.value })}
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="edit-name" className="text-right">Name</Label>
                <Input
                  id="edit-name"
                  value={editingRoomType.name}
                  onChange={(e) => setEditingRoomType({ ...editingRoomType, name: e.target.value })}
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
                  onChange={(e) => setEditingRoomType({ ...editingRoomType, amenities: e.target.value.split(', ') })}
                  className="col-span-3"
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