interface TabMenuProps {
  activeTab: 'fridge' | 'recipes' | 'diary'
  setActiveTab: React.Dispatch<
    React.SetStateAction<'fridge' | 'recipes' | 'diary'>
  >
}

// TabMenu 함수형 컴포넌트
function TabMenu({ activeTab, setActiveTab }: TabMenuProps) {
  return (
    <div className="flex tab-menu justify-between space-x-4 px-6 font-bold">
      <button
        onClick={() => setActiveTab('fridge')}
        className={`${
          activeTab === 'fridge'
            ? 'border-b-4 border-brown-300'
            : 'border-b-4 border-transparent'
        } h-16`}
      >
        마이냉장고
      </button>
      <button
        onClick={() => setActiveTab('recipes')}
        className={`${
          activeTab === 'recipes'
            ? 'border-b-4 border-brown-300'
            : 'border-b-4 border-transparent'
        } h-16`}
      >
        나의 레시피
      </button>
      <button
        onClick={() => setActiveTab('diary')}
        className={`${
          activeTab === 'diary'
            ? 'border-b-4 border-brown-300'
            : 'border-b-4 border-transparent'
        } h-16`}
      >
        요리일기
      </button>
    </div>
  )
}

export default TabMenu
