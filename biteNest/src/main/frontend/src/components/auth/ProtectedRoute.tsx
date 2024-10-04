import { Navigate, Outlet, useLocation } from 'react-router-dom'
import useUser from '@hooks/auth/useUser' // 유저 정보를 가져오는 훅

export function ProtectedRoute() {
  const { user } = useUser() // 로딩 상태 추가
  console.log('protectedeUser', user)
  const location = useLocation()

  if (!user) {
    // 사용자가 로그인되지 않았다면 로그인 페이지로 리다이렉트
    alert('로그인이 필요한 페이지입니다. 로그인 창으로 이동합니다')
    return <Navigate to="/signin" state={{ from: location }} replace />
  }

  // 로그인된 경우, 자식 컴포넌트를 렌더링
  return <Outlet />
}
