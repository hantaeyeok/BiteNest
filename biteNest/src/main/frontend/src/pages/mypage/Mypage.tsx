import { useState } from 'react'
import FridgePage from './Fridge'
import MyRecipePage from './MyRecipe'
import CookingDiaryPage from './CookingDiary'
import TabMenu from '@/components/fridge/shared/TabMenu'
import AddBanner from '@/components/fridge/shared/AddBanner'
import useUser from '@hooks/auth/useUser'

function MyPage() {
  const [activeTab, setActiveTab] = useState<'fridge' | 'recipes' | 'diary'>(
    'fridge',
  ) // 기본 탭은 '마이냉장고'

  const { user } = useUser()

  return (
    <div className="flex justify-center item-center min-h-screen text-brown-300">
      <div className="w-full max-w-[420px] my-12">
        <div className="flex justify-between space-x-4 mb-2">
          <div className="text-lg font-bold">{user?.nickName || ''}</div>
          <div>설정</div>
        </div>
        <AddBanner />
        <TabMenu activeTab={activeTab} setActiveTab={setActiveTab} />
        <div className="p-6 bg-cream rounded-lg min-h-[500px]">
          {activeTab === 'fridge' && <FridgePage />}
          {activeTab === 'recipes' && <MyRecipePage />}
          {activeTab === 'diary' && <CookingDiaryPage />}
        </div>
      </div>
    </div>
  )
}

export default MyPage
