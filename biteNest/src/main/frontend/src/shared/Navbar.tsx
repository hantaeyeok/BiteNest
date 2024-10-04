import { useCallback, useState } from 'react'
import { useLocation } from 'react-router-dom'
import useUser from '@hooks/auth/useUser'
import instance from '@/util/axios'

const Navbar: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false)
  const location = useLocation()
  const showSignButton = !['/signup', '/signin'].includes(location.pathname)

  const { user, setUser } = useUser()
  console.log('user', user)

  const handleLogout = async () => {
    try {
      const token = localStorage.getItem('token')
      const response = await instance.post(
        'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/logout',
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      )
      console.log('로그아웃 성공:', response.data)

      setUser(null)
      localStorage.removeItem('token')
      localStorage.removeItem('email')
      localStorage.removeItem('nickName')
      localStorage.removeItem('id')
    } catch (error) {
      console.error('로그아웃 실패:', error)
    }
  }

  const renderButton = useCallback(() => {
    if (user) {
      return (
        <button
          onClick={handleLogout}
          className="text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
        >
          로그아웃
        </button>
      )
    }

    if (showSignButton) {
      return (
        <a
          href="/signin"
          className="text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
        >
          로그인 / 회원가입
        </a>
      )
    }

    return null
  }, [user, showSignButton])

  const toggleMenu = () => {
    setIsOpen(!isOpen)
  }

  return (
    <nav className="bg-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex">
            <div className="flex-shrink-0 flex items-center font-logo">
              <a href="/" className="text-2xl font-bold text-brown-300">
                BiteNest
              </a>
            </div>

            <div className="hidden md:ml-6 md:flex md:space-x-8">
              <a
                href={`/mypage/${user?.id}`}
                className="flex items-center justify-center text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
              >
                냉장고 관리
              </a>
              <a
                href="/recipes"
                className="flex items-center justify-center text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
              >
                모든 레시피
              </a>
            </div>
          </div>

          <div className="hidden md:flex md:items-center md:space-x-4">
            {renderButton()}
            <a
              href="/mypage"
              className="text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium "
            >
              마이페이지
            </a>
          </div>

          <div className="md:hidden flex items-center">
            <button
              type="button"
              className="text-brown-300 hover:text-brown-100 focus:outline-none"
              onClick={toggleMenu}
            >
              <svg
                className="h-8 w-8"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
            </button>
          </div>
        </div>

        {isOpen && (
          <div className="md:hidden">
            <a
              href={`/mypage/${user?.id}`} // user?.id로 안전하게 접근
              className="block text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
            >
              냉장고관리
            </a>
            <a
              href="/recipes"
              className="block text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
            >
              모든 레시피
            </a>
            {renderButton()}
            <a
              href="/signup"
              className="block text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
            >
              회원가입
            </a>
          </div>
        )}
      </div>
    </nav>
  )
}

export default Navbar
