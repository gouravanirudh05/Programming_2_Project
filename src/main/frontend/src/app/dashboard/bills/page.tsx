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

type Bill = {
  billID: number; // Updated from string to number
  amount: number;
  purchased: string[];
  purchasedList: number[]; // Renamed from purchasedVal to purchasedList
  quantity: number[];
  payed: boolean;
  generatedOn: string;
  payedOn: string | null;
  customerID: string;
  reservationID?: string; // Optional
};

export default function BillManagement() {
  const [bills, setBills] = useState<Bill[]>([]);
  const [editingBill, setEditingBill] = useState<Bill | null>(null);
  const [printableBill, setPrintableBill] = useState<Bill | null>(null);

  // Fetch bills from the backend
  useEffect(() => {
    const fetchBills = async () => {
      try {
        const response = await axios.get('/api/bills/list'); // Replace with your backend endpoint
        setBills(response.data);
      } catch (error) {
        console.error('Error fetching bills:', error);
      }
    };
    fetchBills();
  }, []);

  // Update a bill on the backend
  const updateBill = async () => {
    if (editingBill) {
      try {
        await axios.post(`/api/bills/add/${editingBill.billID}`, editingBill); // Replace with your backend endpoint
        setBills(bills.map((bill) => (bill.billID === editingBill.billID ? editingBill : bill)));
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
                <TableHead>Bill ID</TableHead>
                <TableHead>Amount</TableHead>
                <TableHead>Customer ID</TableHead>
                <TableHead>Reservation ID</TableHead>
                <TableHead>Generated On</TableHead>
                <TableHead>Paid</TableHead>
                <TableHead>Paid On</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {bills.map((bill) => (
                <TableRow key={bill.billID}>
                  <TableCell>{bill.billID}</TableCell>
                  <TableCell>${bill.amount}</TableCell>
                  <TableCell>{bill.customerID}</TableCell>
                  <TableCell>{bill.reservationID || 'N/A'}</TableCell>
                  <TableCell>{new Date(bill.generatedOn).toLocaleString()}</TableCell>
                  <TableCell>{bill.payed ? 'Yes' : 'No'}</TableCell>
                  <TableCell>{bill.payedOn ? new Date(bill.payedOn).toLocaleString() : 'N/A'}</TableCell>
                  <TableCell>
                    <Button variant="outline" size="sm" onClick={() => setEditingBill(bill)} className="mr-2">
                      View/Edit
                    </Button>
                    <Button variant="outline" size="sm" onClick={() => generatePrintableBill(bill)}>
                      Print Bill
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {editingBill && (
        <Dialog open={!!editingBill} onOpenChange={() => setEditingBill(null)}>
          <DialogContent className="max-w-md">
            <DialogHeader>
              <DialogTitle>Bill Details</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div>
                <strong>Bill ID:</strong> {editingBill.billID}
              </div>
              <div>
                <strong>Customer ID:</strong> {editingBill.customerID}
              </div>
              <div>
                <strong>Reservation ID:</strong> {editingBill.reservationID || 'N/A'}
              </div>
              <div>
                <strong>Generated On:</strong> {new Date(editingBill.generatedOn).toLocaleString()}
              </div>
              <div>
  <strong>Items:</strong>
  <ul className="list-disc list-inside">
    {editingBill.purchased.map((item, index) => (
      <li key={index}>
        {item}: ${editingBill.purchasedList[index]} (Quantity: {editingBill.quantity[index]})
      </li>
    ))}
  </ul>
</div>
              <div>
                <strong>Total Amount:</strong> ${editingBill.amount}
              </div>
              <div className="flex items-center space-x-2">
                <Checkbox
                  id="paid"
                  checked={editingBill.payed}
                  onCheckedChange={(checked) => {
                    setEditingBill({
                      ...editingBill,
                      payed: checked as boolean,
                      payedOn: checked ? new Date().toISOString() : null
                    })
                  }}
                />
                <label htmlFor="paid">Paid</label>
              </div>
              {editingBill.payed && (
                <div>
                  <strong>Paid On:</strong> {editingBill.payedOn ? new Date(editingBill.payedOn).toLocaleString() : 'N/A'}
                </div>
              )}
            </div>
            <DialogFooter>
              <Button onClick={updateBill}>Update Bill</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}

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
                <p><strong>Bill ID:</strong> {printableBill.billID}</p>
                <p><strong>Customer ID:</strong> {printableBill.customerID}</p>
                <p><strong>Reservation ID:</strong> {printableBill.reservationID || 'N/A'}</p>
                <p><strong>Generated On:</strong> {format(new Date(printableBill.generatedOn), 'PPpp')}</p>
              </div>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Item</TableHead>
                    <TableHead className="text-right">Amount</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {printableBill.purchased.map((item, index) => (
                    <TableRow key={index}>
                      <TableCell>{item}</TableCell>
                      <TableCell className="text-right">${printableBill.purchasedList[index].toFixed(2)}</TableCell>
                    </TableRow>
                  ))}
                  <TableRow>
                    <TableCell className="font-bold">Total</TableCell>
                    <TableCell className="text-right font-bold">${printableBill.amount.toFixed(2)}</TableCell>
                  </TableRow>
                </TableBody>
              </Table>
              <div className="mt-6">
                <p><strong>Payment Status:</strong> {printableBill.payed ? 'Paid' : 'Unpaid'}</p>
                {printableBill.payedOn && (
                  <p><strong>Paid On:</strong> {format(new Date(printableBill.payedOn), 'PPpp')}</p>
                )}
              </div>
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
  )
}