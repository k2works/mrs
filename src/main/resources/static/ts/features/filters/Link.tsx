import React from 'react'
import { VisibilityFiltersType } from './filtersSlice'

const Link: React.FC<Props> = ({
  active,
  children,
  setVisibilityFilter,
  filter
}) => (
  <button
    onClick={() => setVisibilityFilter(filter)}
    disabled={active}
    style={{
      marginLeft: '4px'
    }}
  >
    {children}
  </button>
)

interface Props {
  active: boolean
  setVisibilityFilter: (filter: VisibilityFiltersType) => any
  filter: VisibilityFiltersType
}

export default Link