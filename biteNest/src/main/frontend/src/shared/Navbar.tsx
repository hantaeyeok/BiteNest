import { useCallback, useState } from 'react'
import { Link, useLocation } from 'react-router-dom'
import useUser from '@hooks/auth/useUser'
import Button from '@/components/shared/Button'
import instance from '@/util/axios'

const Navbar: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false)
  const location = useLocation()
  const showSignButton =
    ['/signup', '/signin'].includes(location.pathname) === false

  const { user, setUser } = useUser()
  console.log('user', user)

  const handleLogout = async () => {
    try {
      // 백엔드에 로그아웃 요청 보내기
      const token = localStorage.getItem('token')
      const response = await instance.post(
        'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/logout',
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer token 추가
          },
        },
      ) // API TODO

      console.log('로그아웃 성공:', response.data)

      // 사용자 상태를 null로 설정하여 로그아웃 처리
      setUser(null)

      // 로컬 스토리지 초기화
      localStorage.removeItem('token')
      localStorage.removeItem('email')
      localStorage.removeItem('nickName')

      // 사용자 상태를 null로 설정
      // 여기서 useUser 훅을 통해 상태를 업데이트해야 할 수 있습니다.
      // 예를 들어, setUser(null)과 같은 함수가 있을 경우 이를 호출합니다.
    } catch (error) {
      console.error('로그아웃 실패:', error)
    }
  }

  const renderButton = useCallback(() => {
    if (user != null) {
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
            {/* 로고 */}
            <div className="flex-shrink-0 flex items-center font-logo">
              <a href="/" className="text-2xl font-bold text-brown-300">
                BiteNest
              </a>
            </div>

            {/* 링크들 (데스크탑용) */}
            <div className="hidden md:ml-6 md:flex md:space-x-8">
              <a
                href="/fridge"
                className="flex items-center justify-center text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
              >
                냉장고관리
              </a>
              <a
                href="/recipes"
                className="flex items-center justify-center text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium"
              >
                모든 레시피
              </a>
            </div>
          </div>

          {/* 우측 로그인/회원가입 (데스크탑용) */}

          <div className="hidden md:flex md:items-center md:space-x-4">
            {renderButton()}
            <a
              href="/mypage"
              className="text-brown-300 hover:text-brown-100 px-3 py-2 rounded-md text-lg font-medium "
            >
              마이페이지
            </a>
          </div>

          {/* 모바일 메뉴 버튼 */}
          <div className="md:hidden flex items-center">
            <button
              type="button"
              className="text-brown-300 hover:text-brown-100 focus:outline-none"
              onClick={toggleMenu}
            >
              {/* 햄버거 아이콘 */}
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

        {/* 모바일 메뉴 (toggle 적용) */}
        {isOpen && (
          <div className="md:hidden">
            <a
              href="/fridge"
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
