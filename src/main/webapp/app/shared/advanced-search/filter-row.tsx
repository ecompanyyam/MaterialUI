import React, { useState } from 'react';
import { Grid, Stack, TextField, Select, MenuItem, InputLabel, SelectChangeEvent, IconButton, FormControl, Menu } from '@mui/material';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

import { IFilterRowProps, IFilterType } from './types';

export const FilterRow = ({ filterOptions, updateFilterRows, removeFilterRow, row: filterRow, rowIndex }: IFilterRowProps) => {
  const [selectedFilterType, setSelectedFilterType] = useState<IFilterType>();

  const filterPropertyOptions = filterOptions.map(option => ({ label: option.label, value: option.value }));

  const getFilter = (val: string) => {
    return filterOptions.find(option => option.value === val);
  };

  return (
    <Grid>
      <Stack direction="row" spacing={8} mt={1} mb={1} justifyContent="start" padding="0px 16px">
        <Select
          sx={{ height: '30px', width: '33%', borderRadius: '30px' }}
          id="property"
          MenuProps={{ MenuListProps: { sx: { p: '8px 0px' } }, sx: { p: '8px 0px' } }}
          onChange={(event: SelectChangeEvent) => {
            const val = event.target.value;
            updateFilterRows({ property: val }, rowIndex);
            setSelectedFilterType(getFilter(val).type);
          }}
          variant="outlined"
          value={filterRow?.property}
        >
          {filterPropertyOptions.map(item => {
            return (
              <MenuItem key={item.value} value={item.value}>
                {item.label}
              </MenuItem>
            );
          })}
        </Select>
        <Select
          id="operator"
          sx={{ width: '33%', height: '30px', borderRadius: '30px' }}
          disabled={!filterRow?.property}
          value={filterRow?.operator}
          variant="outlined"
          onChange={(event: SelectChangeEvent) => {
            updateFilterRows({ property: filterRow.property, operator: event.target.value }, rowIndex);
          }}
        >
          {(getFilter(filterRow?.property)?.operators ?? []).map(option => {
            return (
              <MenuItem key={option.value} value={option.value}>
                {option.label}
              </MenuItem>
            );
          })}
        </Select>
        {selectedFilterType === 'text' ? (
          <TextField
            id="value-text"
            size="small"
            sx={{ width: '33%', height: '30px', borderRadius: '30px' }}
            disabled={!filterRow?.property || !filterRow?.operator}
            onChange={(event: any) => {
              updateFilterRows({ property: filterRow.property, operator: filterRow.operator, value: event.target.value }, rowIndex);
            }}
            placeholder={getFilter(filterRow?.property)?.placeholder ?? 'Enter Text'}
            value={filterRow?.value ?? ''}
          />
        ) : (
          <Select
            id="value-dropdown"
            sx={{ width: '33%', height: '30px', borderRadius: '30px' }}
            disabled={!filterRow?.property || !filterRow?.operator}
            value={filterRow?.value}
            variant="outlined"
            onChange={(event: SelectChangeEvent) => {
              updateFilterRows({ property: filterRow.property, operator: filterRow.operator, value: event.target.value }, rowIndex);
            }}
          >
            {(filterOptions.find(option => option.value === filterRow?.property)?.options ?? []).map(option => {
              return (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              );
            })}
          </Select>
        )}
        <IconButton
          onClick={() => {
            removeFilterRow(rowIndex);
          }}
        >
          <FontAwesomeIcon icon={faXmark} />
        </IconButton>
      </Stack>
    </Grid>
  );
};
