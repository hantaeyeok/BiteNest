import Button from '@components/shared/Button'

interface TipsSectionProps {
  tips?: string[] | null
  handleAddTip: () => void
  handleTipChange: (index: number, value: string) => void // description만 업데이트
  handleRemoveTip: (index: number) => void
}

function TipSection({
  tips,
  handleAddTip,
  handleRemoveTip,
  handleTipChange,
}: TipsSectionProps) {
  return (
    <div>
      {tips &&
        tips.map((tip, index) => (
          <div key={index} className="flex space-x-2 ">
            {' '}
            {/* key는 최상위 요소에 */}
            <input
              type="text"
              className="mt-1 p-2 border border-brown-100 w-full rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
              placeholder="tip 설명"
              value={tip}
              onChange={(e) => handleTipChange(index, e.target.value)}
            />
            <Button
              label="X"
              color="bg-cream text-brown-200"
              size="small"
              onClick={() => handleRemoveTip(index)}
            />
          </div>
        ))}
      <Button
        label="TIP추가"
        color="bg-darkCream text-white"
        size="full"
        onClick={handleAddTip}
      />
    </div>
  )
}

export default TipSection
