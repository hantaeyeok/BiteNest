import { BrowserRouter, Routes, Route } from 'react-router-dom'
import HomePage from '@pages/Home'
import SigninPage from '@pages/Signin'
import SignupPage from '@pages/Signup'
import RecipeCreatePage from '@pages/RecipeCreate'
import MyPage from '@pages/mypage/Mypage'
import Navbar from '@shared/Navbar'
import { ProtectedRoute } from './components/auth/ProtectedRoute'

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* 비보호 경로 */}
        <Route path="/" element={<HomePage />} />
        <Route path="/signin" element={<SigninPage />} />
        <Route path="/signup" element={<SignupPage />} />

        {/* 보호된 경로 */}
        <Route element={<ProtectedRoute />}>
          <Route path="/mypage/:userId" element={<MyPage />} />
          <Route path="/recipes/create" element={<RecipeCreatePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
