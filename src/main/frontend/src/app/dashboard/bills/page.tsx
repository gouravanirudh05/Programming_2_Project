'use client'
import axios from 'axios'
import { useState,useEffect } from 'react'
import { Button } from '@/components/ui/button'
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
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from '@/components/ui/dialog'
import { Checkbox } from '@/components/ui/checkbox'
// } from '@/components/ui/checkbox'
import { format } from 'date-fns'

type Item = {
  name: string;
  price: number;
  quantity: number;
};

type Bill = {
  billId: number;
  payed: boolean;
  customerID: string;
  items: Item[];
  payedOn: string | null; // `null` if not paid
};

export default function BillManagement() {
  const [bills, setBills] = useState<Bill[]>([]);
  const [editingBill, setEditingBill] = useState<Bill | null>(null);
  const [printableBill, setPrintableBill] = useState<Bill | null>(null);

  // Fetch bills from the backend
    const fetchBills = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/bill/list'); // Replace with your backend endpoint
        for (let i = 0; i < response.data.length; i++) {
          response.data[i].items = [];
          response.data[i].customerID = response.data[i].customerID || response.data[i].customerId
          for (let j = 0; j < response.data[i].quantity.length; j++) {
            console.log(response.data[i].purchased[j])
            response.data[i].items.push({
              name: response.data[i].purchased[j],
              price: response.data[i].purchasedList[j],
              quantity: response.data[i].quantity[j],
            });
          }
        }
        setBills(response.data);
      } catch (error) {
        console.error('Error fetching bills:', error);
      }
    };
  useEffect(() => {
    fetchBills();
  }, []);

  // Update a bill on the backend
  const updateBill = async () => {
    if (editingBill) {
      try {
        await axios.put(`http://localhost:8080/api/bill/update/${editingBill.billId}`, editingBill); // Replace with your backend endpoint
        fetchBills()
        setEditingBill(null);
      } catch (error) {
        console.error('Error updating bill:', error);
      }
    }
  };

  const generatePrintableBill = (bill: Bill) => {
    setPrintableBill(bill);
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold tracking-tight">Bill Management</h1>
      <Card>
        <CardHeader>
          <CardTitle>All Bills</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Customer ID</TableHead>
                <TableHead>Items</TableHead>
                <TableHead>Paid</TableHead>
                <TableHead>Paid On</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {bills.map((bill, index) => (
                <TableRow key={index}>
                  <TableCell>{bill.customerID}</TableCell>
                  <TableCell>
                    {bill.items.map((item) => item.name).join(', ')}
                  </TableCell>
                  <TableCell>{bill.payed ? 'Yes' : 'No'}</TableCell>
                  <TableCell>
                    {bill.payedOn ? new Date(bill.payedOn).toLocaleString() : 'N/A'}
                  </TableCell>
                  <TableCell>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => setEditingBill(bill)}
                      className="mr-2"
                    >
                      View/Edit
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => generatePrintableBill(bill)}
                    >
                      Print Bill
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {/* Edit Bill Dialog */}
      {editingBill && (
        <Dialog open={!!editingBill} onOpenChange={() => setEditingBill(null)}>
          <DialogContent className="max-w-md">
            <DialogHeader>
              <DialogTitle>Bill Details</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div>
                <strong>Customer ID:</strong> {editingBill.customerID}
              </div>
              <div>
                <strong>Items:</strong>
                <ul className="list-disc list-inside">
                  {editingBill.items.map((item, index) => (
                    <li key={index}>
                      {item.name}: ${item.price.toFixed(2)} (Quantity: {item.quantity})
                    </li>
                  ))}
                </ul>
              </div>
              <div className="flex items-center space-x-2">
                <Checkbox
                  id="paid"
                  checked={editingBill.payed}
                  onCheckedChange={async (checked) => {
                    const updatedBill = {
                      ...editingBill,
                      payed: checked as boolean,
                      payedOn: checked ? new Date().toISOString() : null,
                    };
                    setEditingBill(updatedBill);
                    try {
                      await axios.post(`http://localhost:8080/api/bill/paid/${editingBill.billId}`, {"payed":editingBill.payed});
                    } catch (error) {
                      console.error('Error updating payment status:', error);
                    }
                  }}
                />
                <label htmlFor="paid">Paid</label>
              </div>
              {editingBill.payed && (
                <div>
                  <strong>Paid On:</strong>{' '}
                  {editingBill.payedOn
                    ? new Date(editingBill.payedOn).toLocaleString()
                    : 'N/A'}
                </div>
              )}
            </div>
            <DialogFooter>
              <Button onClick={updateBill}>Update Bill</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}

      {/* Printable Bill Dialog */}
      {printableBill && (
        <Dialog open={!!printableBill} onOpenChange={() => setPrintableBill(null)}>
          <DialogContent className="max-w-3xl">
            <DialogHeader>
              <DialogTitle>Printable Bill</DialogTitle>
            </DialogHeader>
            <div className="p-6 bg-white" id="printable-bill">
              <div className="text-center mb-6">
                <h2 className="text-2xl font-bold">Hotel Management System</h2>
                <p>123 Hotel Street, City, Country</p>
                <p>Phone: +1 234 567 890 | Email: info@hotel.com</p>
              </div>
              <div className="mb-6">
                <h3 className="text-xl font-semibold mb-2">Bill Details</h3>
                <p><strong>Customer ID:</strong> {printableBill.customerID}</p>
                <p>
                  <strong>Paid On:</strong>{' '}
                  {printableBill.payedOn
                    ? new Date(printableBill.payedOn).toLocaleString()
                    : 'N/A'}
                </p>
              </div>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Item</TableHead>
                    <TableHead>Price</TableHead>
                    <TableHead>Quantity</TableHead>
                    <TableHead>Total</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {printableBill.items.map((item, index) => (
                    <TableRow key={index}>
                      <TableCell>{item.name}</TableCell>
                      <TableCell>${item.price.toFixed(2)}</TableCell>
                      <TableCell>{item.quantity}</TableCell>
                      <TableCell>${(item.price * item.quantity).toFixed(2)}</TableCell>
                    </TableRow>
                  ))}
                  <TableRow>
                    <TableCell className="font-bold">Total</TableCell>
                    <TableCell />
                    <TableCell />
                    <TableCell className="font-bold">
                      $
                      {printableBill.items
                        .reduce((total, item) => total + item.price * item.quantity, 0)
                        .toFixed(2)}
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
              <div className="mt-8 text-center text-sm text-gray-500">
                <p>Thank you for choosing our hotel. We hope you enjoyed your stay!</p>
              </div>
            </div>
            <DialogFooter>
              <Button onClick={() => window.print()}>Print Bill</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  );
}