"use client";

import { useState, useEffect } from "react";
import axios from "axios";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

type Dish = {
  dishID: number;
  name: string;
  price: number;
  dishType: string;
  calories?: number;
  preparationTime?: number;
  isAvailable?: boolean;
};

export default function DishManagement() {
  const [dishes, setDishes] = useState<Dish[]>([]);
  const [newDish, setNewDish] = useState<Dish>({
    dishID: -1,
    name: "",
    price: 0,
    dishType: "",
    calories: 0,
    preparationTime: 0,
    isAvailable: false,
  });
  const [isAddDishOpen, setIsAddDishOpen] = useState(false);
  const [editingDish, setEditingDish] = useState<Dish | null>(null);

  // Fetch dishes from backend
  const fetchDishes = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/dish/all");
      setDishes(response.data);
    } catch (error) {
      console.error("Error fetching dishes:", error);
    }
  };

  // Add a new dish
  const addDish = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/dish/add", newDish);
      fetchDishes();
      // setDishes([...dishes, response.data]);
      setNewDish({ dishID: -1, name: "", price: 0, dishType: "" });
      setIsAddDishOpen(false);
    } catch (error) {
      console.error("Error adding dish:", error);
    }
  };

  // Update an existing dish
  const updateDish = async () => {
    if (editingDish) {
      try {
        const response = await axios.post(
          `http://localhost:8080/api/dish/update/${editingDish.dishID}`,
          editingDish
        );
        fetchDishes();
        setEditingDish(null);
      } catch (error) {
        console.error("Error updating dish:", error);
      }
    }
  };

  // Delete a dish
  const deleteDish = async (dishID: number) => {
    try {
      await axios.post(`http://localhost:8080/api/dish/remove/${dishID}`);
      fetchDishes();
    } catch (error) {
      console.error("Error deleting dish:", error);
    }
  };

  // Fetch dishes on component mount
  useEffect(() => {
    fetchDishes();
  }, []);

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
          <SelectItem value="APPETIZER">APPETIZER</SelectItem>
          <SelectItem value="MAIN_COURSE">Main Course</SelectItem>
          <SelectItem value="DESSERT">DESSERT</SelectItem>
          <SelectItem value="BEVERAGE">BEVERAGE</SelectItem>
        </SelectContent>
      </Select>
    </div>
    <div className="grid grid-cols-4 items-center gap-4">
      <Label htmlFor="calories" className="text-right">Calories</Label>
      <Input
        id="calories"
        type="number"
        value={newDish.calories || ""}
        onChange={(e) => setNewDish({ ...newDish, calories: parseInt(e.target.value, 10) })}
        className="col-span-3"
      />
    </div>
    <div className="grid grid-cols-4 items-center gap-4">
      <Label htmlFor="preparationTime" className="text-right">Preparation Time (mins)</Label>
      <Input
        id="preparationTime"
        type="number"
        value={newDish.preparationTime || ""}
        onChange={(e) => setNewDish({ ...newDish, preparationTime: parseInt(e.target.value, 10) })}
        className="col-span-3"
      />
    </div>
    <div className="grid grid-cols-4 items-center gap-4">
      <Label htmlFor="isAvailable" className="text-right">Available</Label>
      <Select
        onValueChange={(value) =>
          setNewDish({ ...newDish, isAvailable: value === "true" })
        }
        value={newDish.isAvailable ? "true" : "false"}
      >
        <SelectTrigger className="col-span-3">
          <SelectValue placeholder="Is the dish available?" />
        </SelectTrigger>
        <SelectContent>
          <SelectItem value="true">Yes</SelectItem>
          <SelectItem value="false">No</SelectItem>
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
                <Button
                  variant="outline"
                  size="sm"
                  className="mr-2"
                  onClick={() => setEditingDish(dish)}
                >
                  Edit
                </Button>
                <Button
                  variant="destructive"
                  size="sm"
                  onClick={() => deleteDish(dish.dishID)}
                >
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
        onValueChange={(value) => setEditingDish({ ...editingDish, dishType: value })}
        value={editingDish.dishType}
      >
        <SelectTrigger className="col-span-3">
          <SelectValue placeholder="Select dish type" />
        </SelectTrigger>
        <SelectContent>
          <SelectItem value="APPETIZER">APPETIZER</SelectItem>
          <SelectItem value="MAIN_COURSE">Main Course</SelectItem>
          <SelectItem value="DESSERT">DESSERT</SelectItem>
          <SelectItem value="BEVERAGE">BEVERAGE</SelectItem>
        </SelectContent>
      </Select>
    </div>
    <div className="grid grid-cols-4 items-center gap-4">
      <Label htmlFor="edit-calories" className="text-right">Calories</Label>
      <Input
        id="edit-calories"
        type="number"
        value={editingDish.calories || ""}
        onChange={(e) =>
          setEditingDish({ ...editingDish, calories: parseInt(e.target.value, 10) })
        }
        className="col-span-3"
      />
    </div>
    <div className="grid grid-cols-4 items-center gap-4">
      <Label htmlFor="edit-preparationTime" className="text-right">Preparation Time (mins)</Label>
      <Input
        id="edit-preparationTime"
        type="number"
        value={editingDish.preparationTime || ""}
        onChange={(e) =>
          setEditingDish({
            ...editingDish,
            preparationTime: parseInt(e.target.value, 10),
          })
        }
        className="col-span-3"
      />
    </div>
    <div className="grid grid-cols-4 items-center gap-4">
      <Label htmlFor="edit-isAvailable" className="text-right">Available</Label>
      <Select
        onValueChange={(value) =>
          setEditingDish({ ...editingDish, isAvailable: value === "true" })
        }
        value={editingDish.isAvailable ? "true" : "false"}
      >
        <SelectTrigger className="col-span-3">
          <SelectValue placeholder="Is the dish available?" />
        </SelectTrigger>
        <SelectContent>
          <SelectItem value="true">Yes</SelectItem>
          <SelectItem value="false">No</SelectItem>
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
  );
}
