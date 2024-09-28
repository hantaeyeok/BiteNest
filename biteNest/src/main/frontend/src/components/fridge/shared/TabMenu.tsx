interface TabMenuProps {
  setActiveTab: React.Dispatch<
    React.SetStateAction<'fridge' | 'recipes' | 'diary'>
  >
}

// TabMenu 함수형 컴포넌트
function TabMenu({ setActiveTab }: TabMenuProps) {
  return (
    <div className="flex tab-menu justify-between space-x-4 m-4">
      <button onClick={() => setActiveTab('fridge')}>마이냉장고</button>
      <button onClick={() => setActiveTab('recipes')}>나의 레시피</button>
      <button onClick={() => setActiveTab('diary')}>요리일기</button>
    </div>
  )
}

export default TabMenu
