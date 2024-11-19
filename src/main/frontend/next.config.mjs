/** @type {import('next').NextConfig} */
const nextConfig = {
    output: 'export',
    distDir: "build",
    reactStrictMode: true,
  async rewrites() {
    return [
      {
        source: '/api/:path*',  // Proxy all API requests starting with /api
        destination: 'http://localhost:8080/api/:path*',  // Forward to Spring Boot
      },
    ];
  },
  };

export default nextConfig;