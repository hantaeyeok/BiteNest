import { useRecoilValue, useSetRecoilState } from 'recoil'
import { userAtom } from '@atoms/user'

function useUser() {
  const user = useRecoilValue(userAtom)
  const setUser = useSetRecoilState(userAtom)

  return { user, setUser } // 사용자 정보와 설정 함수를 반환
}

export default useUser
