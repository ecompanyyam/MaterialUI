import React, { useState } from "react";
import { Grid, Stack, TextField, Select, MenuItem, InputLabel, SelectChangeEvent, IconButton } from "@mui/material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";

import { IFilterRowProps, IFilterType } from "./types";

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
                    sx={{ height: '40px', width: '33%' }}
                    label={<InputLabel>Select Property</InputLabel>}
                    MenuProps={{ MenuListProps: { sx: { paddingLeft: 0 }} }}
                    onChange={(event: SelectChangeEvent) => {
                        const val = event.target.value;
                        updateFilterRows({ property: val, removable: true }, rowIndex);
                        setSelectedFilterType(getFilter(val).type);
                    }}
                    value={filterRow?.property}
                    >
                    {filterPropertyOptions.map(item => {
                        return (
                            <MenuItem key={item.value} value={item.value}>{item.label}</MenuItem>
                        );
                    })}
                </Select>
                <Select
                    sx={{ width: '33%', height: '40px' }}
                    disabled={!filterRow?.property}
                    value={filterRow?.operator}
                    onChange={(event: SelectChangeEvent) => {
                    updateFilterRows({ property: filterRow.property, operator: event.target.value, removable: true }, rowIndex);
                }}>
                    {(getFilter(filterRow?.property)?.operators ?? []).map(option => {
                        return (
                            <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem> 
                        );
                    })}
                </Select>
                {selectedFilterType === "text"
                    ? (
                        <TextField
                        size="small"
                        sx={{ width: '33%', height: '40px' }}
                        disabled={!filterRow?.property || !filterRow?.operator}
                        onChange={(event: any) => {
                            updateFilterRows(
                                { property: filterRow.property, operator: filterRow.operator, value: event.target.value, removable: true },
                                rowIndex
                            );
                        }}
                        placeholder={getFilter(filterRow?.property)?.placeholder ?? "Enter Text"}
                        value={filterRow?.value ?? ""} />
                    ) : (
                        <Select
                            sx={{ width: '33%', height: '40px' }}
                            disabled={!filterRow?.property || !filterRow?.operator}
                            value={filterRow?.value}
                            label={getFilter(filterRow?.property)?.placeholder ?? "Select any option"}
                            onChange={(event: SelectChangeEvent) => {
                            updateFilterRows({ property: filterRow.property, operator: filterRow.operator, value: event.target.value, removable: true }, rowIndex);
                        }}>
                            {(filterOptions.find(option => option.value === filterRow?.property)?.options ?? []).map(option => {
                                return (
                                    <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem> 
                                );
                            })}
                        </Select>
                    )}
                <IconButton onClick={() => { removeFilterRow(rowIndex); }}>
                    <FontAwesomeIcon icon={faXmark} />
                </IconButton>
            </Stack>
        </Grid>
    )
};
