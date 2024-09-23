interface SpacingProps {
  size: number // 크기 (2, 4, 6, 8 등)
  direction?: 'vertical' | 'horizontal' // 방향 설정
}

const Spacing: React.FC<SpacingProps> = ({ size, direction = 'vertical' }) => {
  // Tailwind CSS 클래스 초기화
  let spacingClass = ''

  // 방향에 따른 클래스 설정
  if (direction === 'horizontal') {
    spacingClass = `mx-${size}` // 기본값 수평 간격 처리
  } else {
    spacingClass = `mb-${size}`
  }

  return <div className={spacingClass}></div>
}

export default Spacing
