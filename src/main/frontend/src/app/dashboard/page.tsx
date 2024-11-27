'use client'
import { useState, useEffect } from 'react'
import axios from 'axios'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { Skeleton } from '@/components/ui/skeleton'
import { Progress } from '@/components/ui/progress'
import { AlertCircle, Users, Home, CalendarCheck, DollarSign, Percent, TrendingUp, BedDouble } from 'lucide-react'
import Image from 'next/image'
type DashboardStats = {
  activeReservations: number
  availableRooms: number
  totalStaff: number
  totalReservations: number
  occupancyRate: number
  hotelAddress: string
  averageDailyRate: number
  todayCheckIns: number
  hotelName: string
  totalRooms: number
  todaysRevenue: number
}

export default function DashboardOverview() {
  const [stats, setStats] = useState<DashboardStats | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchDashboardStats = async () => {
      try {
        const response = await axios.get<DashboardStats>('http://localhost:8080/api/dashboard')
        setStats(response.data)
        setLoading(false)
      } catch (err) {
        setError('Failed to fetch dashboard statistics')
        setLoading(false)
      }
    }

    fetchDashboardStats()
  }, [])

  if (loading) {
    return (
      <div className="space-y-4">
        <Skeleton className="h-12 w-[300px]" />
        <Skeleton className="h-4 w-[200px]" />
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          {[...Array(8)].map((_, i) => (
            <Card key={i}>
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">
                  <Skeleton className="h-4 w-[150px]" />
                </CardTitle>
              </CardHeader>
              <CardContent>
                <Skeleton className="h-8 w-[100px]" />
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <Alert variant="destructive">
        <AlertCircle className="h-4 w-4" />
        <AlertTitle>Error</AlertTitle>
        <AlertDescription>{error}</AlertDescription>
      </Alert>
    )
  }

  if (!stats) {
    return null
  }

  const occupancyRateNumber = stats.occupancyRate

  return (
    <div className="space-y-6">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">{stats.hotelName} Dashboard</h1>
          <p className="text-muted-foreground">{stats.hotelAddress}</p>
        </div>
        <Card className="w-full md:w-auto mt-4 md:mt-0">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Today's Revenue</CardTitle>
            <DollarSign className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">${stats.todaysRevenue.toFixed(2)}</div>
            <p className="text-xs text-muted-foreground">+2.5% from yesterday</p>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Active Reservations</CardTitle>
            <CalendarCheck className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.activeReservations}</div>
            <Progress value={(stats.activeReservations / stats.totalRooms) * 100} className="mt-2" />
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Available Rooms</CardTitle>
            <Home className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.availableRooms}</div>
            <Progress value={(stats.availableRooms / stats.totalRooms) * 100} className="mt-2" />
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Staff</CardTitle>
            <Users className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.totalStaff}</div>
            <p className="text-xs text-muted-foreground">Optimal staff ratio achieved</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Reservations</CardTitle>
            <CalendarCheck className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.totalReservations}</div>
            <p className="text-xs text-muted-foreground">+5% from last month</p>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <Card className="col-span-4">
          <CardHeader>
            <CardTitle>Hotel Overview</CardTitle>
          </CardHeader>
          <CardContent className="pl-2">
            <Image
              src="https://media.istockphoto.com/id/119926339/photo/resort-swimming-pool.jpg?s=612x612&w=0&k=20&c=9QtwJC2boq3GFHaeDsKytF4-CavYKQuy1jBD2IRfYKc="
              alt="Hotel Overview"
              width={800}
              height={400}
              className="rounded-md object-cover"
            />
          </CardContent>
        </Card>
        <Card className="col-span-3">
          <CardHeader>
            <CardTitle>Key Metrics</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="flex items-center">
                <Percent className="h-4 w-4 text-muted-foreground mr-2" />
                <div className="flex-1">
                  <div className="flex justify-between">
                    <div className="text-sm font-medium">Occupancy Rate</div>
                    <div className="text-sm font-medium">{stats.occupancyRate}</div>
                  </div>
                  <Progress value={occupancyRateNumber} className="mt-2" />
                </div>
              </div>
              <div className="flex items-center">
                <DollarSign className="h-4 w-4 text-muted-foreground mr-2" />
                <div className="flex-1">
                  <div className="flex justify-between">
                    <div className="text-sm font-medium">Average Daily Rate</div>
                    <div className="text-sm font-medium">${stats.averageDailyRate.toFixed(2)}</div>
                  </div>
                  <Progress value={(stats.averageDailyRate / 300) * 100} className="mt-2" />
                </div>
              </div>
              <div className="flex items-center">
                <TrendingUp className="h-4 w-4 text-muted-foreground mr-2" />
                <div className="flex-1">
                  <div className="flex justify-between">
                    <div className="text-sm font-medium">RevPAR</div>
                    <div className="text-sm font-medium">
                      ${((stats.averageDailyRate * stats.occupancyRate) / 100).toFixed(2)}
                    </div>
                  </div>
                  <Progress value={((stats.averageDailyRate * stats.occupancyRate) / 30000) * 100} className="mt-2" />
                </div>
              </div>
              <div className="flex items-center">
                <BedDouble className="h-4 w-4 text-muted-foreground mr-2" />
                <div className="flex-1">
                  <div className="flex justify-between">
                    <div className="text-sm font-medium">Today's Check-ins</div>
                    <div className="text-sm font-medium">{stats.todayCheckIns}</div>
                  </div>
                  <Progress value={(stats.todayCheckIns / stats.totalRooms) * 100} className="mt-2" />
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}