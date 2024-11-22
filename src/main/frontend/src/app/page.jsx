import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { BedDouble, Users, Utensils, Calendar, CreditCard, UserCog } from 'lucide-react'

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-100 to-white">
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
          <h1 className="text-3xl font-bold text-gray-900">Hotel Management System</h1>
          <Link href="/login">
            <Button>Go to Login</Button>
          </Link>
        </div>
      </header>
      <main>
        <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
          <div className="px-4 py-6 sm:px-0">
            <div className="text-center mb-12">
              <h2 className="text-4xl font-extrabold text-gray-900 sm:text-5xl sm:tracking-tight lg:text-6xl">
                Streamline Your Hotel Operations
              </h2>
              <p className="mt-5 max-w-xl mx-auto text-xl text-gray-500">
                Manage rooms, reservations, staff, and more with our comprehensive hotel management system.
              </p>
            </div>
            <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
              <FeatureCard
                icon={<BedDouble className="h-6 w-6" />}
                title="Room Management"
                description="Efficiently manage room types, availability, and maintenance schedules."
              />
              <FeatureCard
                icon={<Users className="h-6 w-6" />}
                title="Customer Management"
                description="Keep track of guest information, preferences, and booking history."
              />
              <FeatureCard
                icon={<Calendar className="h-6 w-6" />}
                title="Reservation System"
                description="Streamline the booking process and manage reservations with ease."
              />
              <FeatureCard
                icon={<Utensils className="h-6 w-6" />}
                title="Restaurant Management"
                description="Handle restaurant orders, table reservations, and menu items."
              />
              <FeatureCard
                icon={<CreditCard className="h-6 w-6" />}
                title="Billing & Invoicing"
                description="Generate and manage bills for rooms, services, and restaurant orders."
              />
              <FeatureCard
                icon={<UserCog className="h-6 w-6" />}
                title="Staff Management"
                description="Manage employee information, schedules, and roles efficiently."
              />
            </div>
          </div>
        </div>
      </main>
    </div>
  )
}

function FeatureCard({ icon, title, description }) {
  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center">
          {icon}
          <span className="ml-2">{title}</span>
        </CardTitle>
      </CardHeader>
      <CardContent>
        <CardDescription>{description}</CardDescription>
      </CardContent>
    </Card>
  )
}