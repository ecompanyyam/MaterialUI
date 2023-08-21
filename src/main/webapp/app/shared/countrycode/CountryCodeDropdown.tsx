import React from 'react';
import { ValidatedField } from 'react-jhipster';

interface CountryOption {
  value: string;
  label: string;
  flag: string;
}

interface CountryCodeDropdownProps {
  value: string;
  onChange: (value: string) => void;
  countryOptions: CountryOption[];
}

const CountryCodeDropdown: React.FC<CountryCodeDropdownProps> = ({ value, onChange, countryOptions }) => {
  return (
    <div style={{ display: 'flex', alignItems: 'center', marginLeft: '10px' }}>
      <ValidatedField
        name="countryCode"
        type="select"
        className="validated-field-container"
        value={value}
        onChange={event => onChange(event.target.value)}
      >
        <option value="" />
        {countryOptions.map(option => (
          <option value={option.value} key={option.value}>
            {option.flag} {option.label}
          </option>
        ))}
      </ValidatedField>
    </div>
  );
};

export default CountryCodeDropdown;
