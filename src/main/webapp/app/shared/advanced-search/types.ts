import { Dispatch, SetStateAction } from "react";

interface Option {
    label: string,
    value: string,
};

type IFilterType = 'text' | 'dropdown';

interface IFilter {
    label: string,
    value: string;
    operators: Option[];
    type: IFilterType;
    options?: Option[];
    placeholder?: string;
};

interface IFilterRow {
    property?: string;
    operator?: string;
    value?: string;
};

interface IFilterRowProps {
    filterOptions: IFilter[];
    updateFilterRows: (row: IFilterRow, rowIndex: number) => void;
    removeFilterRow: (rowIndex: number) => void;
    row: IFilterRow;
    rowIndex: number;
};

interface IAdvancedSearchProps {
    filters: IFilter[];
    placeholder: string;
    onApply: (appliedFilters: IFilterRow[]) => void;
    onReset: () => void;
    onBasicTextSearch: (searchKey: string) => void;
};

export type { IFilter, IAdvancedSearchProps, IFilterRow, IFilterRowProps, IFilterType };