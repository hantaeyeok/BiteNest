import { MouseEvent } from 'react'

function Agreement({ children }: { children: React.ReactNode }) {
  return <ul>{children}</ul>
}

function AgreementTitle({
  children,
  checked,
  onChange,
}: {
  children: React.ReactNode
  checked: boolean
  onChange: (e: MouseEvent<HTMLElement>, checked: boolean) => void
}) {
  return (
    <li
      className="text-sm text-brown-100 flex cursor-pointer"
      onClick={(e) => onChange(e, !checked)}
    >
      <IconCheck checked={checked} />
      <p className="font-bold">{children}</p>
    </li>
  )
}

function AgreementDescription({
  children,
  checked,
  onChange,
  link,
}: {
  children: React.ReactNode
  checked: boolean
  onChange: (e: MouseEvent<HTMLElement>, checked: boolean) => void
  link?: string
}) {
  return (
    <li
      className="text-xs text-brown-100 flex space-x-32 cursor-pointer"
      onClick={(e) => onChange(e, !checked)}
    >
      <div className="flex">
        <IconCheck checked={checked} />
        <p>{children}</p>
      </div>
      {link != null ? (
        <a href={link} target="_blank" rel="noreferrer">
          링크
        </a>
      ) : null}
    </li>
  )
}

Agreement.Title = AgreementTitle
Agreement.Description = AgreementDescription

function IconCheck({ checked }: { checked: boolean }) {
  return (
    <svg
      height="17px"
      version="1.1"
      viewBox="0 0 20 20"
      width="17px"
      className={checked ? 'text-green-100 mx-2' : 'text-cream mx-2'}
    >
      <title />
      <desc />
      <defs />
      <g fill="none" id="Page-1" stroke="none">
        <g id="Core" transform="translate(-44.000000, -86.000000)">
          <g id="check-circle" transform="translate(44.000000, 86.000000)">
            <path
              d="M10,0 C4.5,0 0,4.5 0,10 C0,15.5 4.5,20 10,20 C15.5,20 20,15.5 20,10 C20,4.5 15.5,0 10,0 L10,0 Z M8,15 L3,10 L4.4,8.6 L8,12.2 L15.6,4.6 L17,6 L8,15 L8,15 Z"
              id="Shape"
              fill="currentColor"
            />
          </g>
        </g>
      </g>
    </svg>
  )
}

export default Agreement
