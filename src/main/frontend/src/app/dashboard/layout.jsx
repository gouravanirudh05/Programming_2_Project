'use client'

import { useState } from 'react'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { Tabs, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { Building2, UtensilsCrossed, Users, Calendar, BedDouble, Receipt } from 'lucide-react'

export default function DashboardLayout({
  children,
}) {
  const pathname = usePathname()
  const [activeTab, setActiveTab] = useState(pathname.split('/').pop() || 'overview')

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <div className="w-64 bg-white border-r p-4">
        <div className="mb-8">
          <h2 className="text-2xl font-bold text-primary">Grand Hotel</h2>
          <p className="text-sm text-muted-foreground">123 Main Street, New York, NY</p>
        </div>
        <Tabs value={activeTab} onValueChange={setActiveTab} orientation="vertical" className="w-full">
          <TabsList className="flex flex-col h-full gap-2">
            <TabsTrigger value="overview" asChild>
              <Link href="/dashboard" className="flex items-center gap-2">
                <Building2 className="h-4 w-4" />
                Overview
              </Link>
            </TabsTrigger>
            <TabsTrigger value="rooms" asChild>
              <Link href="/dashboard/rooms" className="flex items-center gap-2">
                <BedDouble className="h-4 w-4" />
                Rooms
              </Link>
            </TabsTrigger>
            <TabsTrigger value="reservations" asChild>
              <Link href="/dashboard/reservations" className="flex items-center gap-2">
                <Calendar className="h-4 w-4" />
                Reservations
              </Link>
            </TabsTrigger>
            <TabsTrigger value="staff" asChild>
              <Link href="/dashboard/staff" className="flex items-center gap-2">
                <Users className="h-4 w-4" />
                Staff
              </Link>
            </TabsTrigger>
            <TabsTrigger value="restaurant" asChild>
              <Link href="/dashboard/restaurant" className="flex items-center gap-2">
                <UtensilsCrossed className="h-4 w-4" />
                Restaurant
              </Link>
            </TabsTrigger>
            {/* <TabsTrigger value="customers" asChild>
              <Link href="/dashboard/customers" className="flex items-center gap-2">
                <Users className="h-4 w-4" />
                Customers
              </Link>
            </TabsTrigger> */}
            <TabsTrigger value="bills" asChild>
              <Link href="/dashboard/bills" className="flex items-center gap-2">
                <Receipt className="h-4 w-4" />
                Bills
              </Link>
            </TabsTrigger>
          </TabsList>
        </Tabs>
      </div>

      {/* Main Content */}
      <div className="flex-1 p-8 overflow-auto">
        {children}
      </div>
    </div>
  )}
