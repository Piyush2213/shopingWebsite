import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173, // Specify the port Vite should use
    host: '0.0.0.0', // Allow access from any network interface
  },
  build: {
    outDir: 'dist', // Specify the output directory for production builds
    sourcemap: true, // Generate source maps
  },
});
