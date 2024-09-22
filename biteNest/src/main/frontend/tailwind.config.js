/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./index.html', './src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    container: {
      center: true,
      padding: {
        DEFAULT: '1rem',
        sm: '2rem',
        lg: '4rem',
        xl: '5rem',
        '2xl': '6rem',
      },
    },
    extend: {
      colors: {
        brown: {
          100: '#A0522D',
          200: '#DEB887',
          300: '#8B4513',
        },
        Salmon: {
          100: '#FFA07A',
          200: '#FFDAB9',
        },
        caramel: '#CD853F',
        creamWhite: '#FFF9E5',
        cream: '#F7ECE1',
        textBrown: {
          100: '#3F2514',
          200: '#6D4226',
        },
        green: '#789C00',
        red: '#FF6243',
        yellow: '#FFD600',
        blue: '#437FA1',
      },
      fontFamily: {
        logo: ['MapoHongdaeFreedom', 'sans-serif'],
        sans: ['Inter', 'sans-serif'], // You can customize this further
      },
    },
  },
  plugins: [],
}
