import React, { useState } from "react";
import { Translate } from "react-jhipster";
import { Row, Collapse } from "reactstrap";
import { Button, Chip, Grid, Link, Stack, TextField } from "@mui/material";
import Paper from '@mui/material/Paper';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronUp, faChevronDown } from "@fortawesome/free-solid-svg-icons";

import { FilterRow } from "./filter-row";
import { IAdvancedSearchProps, IFilter, IFilterRow } from "./types";

const initialFilterRow: IFilterRow = {
    property: null,
    operator: null,
    value: null,
}

export const AdvancedSearch = ({ filters, placeholder, onApply, onReset, onBasicTextSearch }: IAdvancedSearchProps) => {
    const [isOpen, setIsOpen] = useState(false);
    const [filterRows, setFilterRows] = useState<IFilterRow[]>([initialFilterRow]);
    const [appliedFilters, setAppliedFilters] = useState<IFilterRow[]>([]);
    const [basicSearchKey, setBasicSearchKey] = useState("");

    const updateFilterRows = (updatedRow: IFilterRow, rowIndex: number) => {
        const newFilterRows = filterRows.map((row, index) => {
            if (index === rowIndex) {
                return updatedRow;
            }

            return row;
        });

        setFilterRows(newFilterRows);
    };

    const removeFilterRow = (rowIndex: number) => {
        const newFilterRows = filterRows.filter((_, index) => index !== rowIndex);
        setFilterRows(filterRows.length === 1 ? [initialFilterRow] : newFilterRows);
    };

    const isAnyFilterRowEmpty = () => {
        return filterRows.some(({ property, operator, value }) => !property || !operator || !value); 
    };

    const handleBasicSearching = (event: any) => {
        onBasicTextSearch(basicSearchKey);
        event.preventDefault();
    }

    return (
        <div>
            <Grid margin={"8px 0px"}>
                <Stack direction="row" gap={2}>
                    <Paper
                        component="form"
                        onSubmit={handleBasicSearching}
                        sx={{ display: 'flex', alignItems: 'center', width: 600, height: '40px', marginBottom: '8px' }}>
                        <Button
                            sx={{height: '40px'}}
                            variant="outlined"
                            size="medium"
                            onClick={() => {
                                setIsOpen(!isOpen)
                            }}
                            endIcon={isOpen ? <FontAwesomeIcon icon={faChevronUp} /> : <FontAwesomeIcon icon={faChevronDown} />}>
                            <Translate contentKey="eCompanyApp.advancedSearch.filtersLabel" />
                        </Button>
                        <TextField
                            variant="outlined"
                            inputMode="search"
                            placeholder={placeholder}
                            size="small"
                            sx={{ flex: 1, ml: 1, mr: 1, "& fieldset": { border: 'none' }, height: '40px' }}
                            onChange={(event: any) => {
                                setBasicSearchKey(event.target.value.trim());
                            }}
                            value={basicSearchKey}
                        />
                        
                    </Paper>
                    {appliedFilters.length > 0 && <Link
                        component="button"
                        variant="body2"
                        onClick={() => {
                            onReset();
                            setAppliedFilters([]);
                            setFilterRows([initialFilterRow]);
                        }}>
                        <Translate contentKey="eCompanyApp.advancedSearch.clearAppliedFiltersLinkButtonLabel" />
                    </Link>}
                </Stack>
                <Collapse isOpen={isOpen}>
                        <Row>
                            {filterRows.map((row, index) => {
                                return <FilterRow
                                    key={`filter-row-${index}`}
                                    rowIndex={index}
                                    row={row}
                                    filterOptions={filters}
                                    updateFilterRows={updateFilterRows}
                                    removeFilterRow={removeFilterRow}
                                />;
                            })}
                        </Row>
                        <Row>
                            <Stack direction="row" spacing={2} justifyContent="flex-end">
                                <Button variant="contained"
                                    disabled={isAnyFilterRowEmpty()}
                                    onClick={(event: any) => {
                                        onApply(filterRows);
                                        setAppliedFilters(filterRows);
                                        event.preventDefault();
                                    }}>
                                    <Translate contentKey="eCompanyApp.advancedSearch.applyButtonLabel" />
                                </Button>
                                <Button
                                    variant="outlined"
                                    onClick={() => {
                                        setFilterRows([...filterRows, initialFilterRow ]);
                                    }}
                                >
                                    <Translate contentKey="eCompanyApp.advancedSearch.addButtonLabel" />
                                </Button>
                                <Button
                                    variant="text"
                                    onClick={() => {
                                        setFilterRows([initialFilterRow]);
                                        onReset();
                                        setAppliedFilters([]);
                                    }}
                                >
                                    <Translate contentKey="eCompanyApp.advancedSearch.resetButtonLabel" />
                                </Button>
                            </Stack>
                        </Row>
                </Collapse>
            </Grid>
        </div>
    )
};
