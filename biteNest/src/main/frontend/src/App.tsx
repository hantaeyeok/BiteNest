import { BrowserRouter, Routes, Route } from 'react-router-dom'
import HomePage from '@pages/Home'
import SigninPage from '@pages/Signin'
import SignupPage from './pages/Signup'

import Navbar from '@shared/Navbar'

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" Component={HomePage} />
        <Route path="/signin" Component={SigninPage} />
        <Route path="/signup" Component={SignupPage} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
