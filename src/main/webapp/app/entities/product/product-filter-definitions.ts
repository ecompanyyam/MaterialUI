import { IFilterType } from 'app/shared/advanced-search/types';

const Equals = { label: 'Equals', value: 'equals' };
const NotEquals =  { label: 'Not Equals', value: 'notEquals' };
const GreaterThan = { label: 'Greater Than', value: 'greaterThan' };
const LessThan =  { label: 'Less Than', value: 'lessThan '};
const GreaterThanOrEqual =  { label: 'Greater Than or Equal', value: 'greaterThanOrEqual' };
const LessThanOrEqual =  { label: 'Less Than or Equal', value: 'lessThanOrEqual' };

const Contains = { label: 'Contains', value: 'contains' };
const DoesNotContain = { label: 'Does Not Contain', value: 'doesNotContain' };

const NumericalOperators = [Equals, NotEquals, GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual];
const TextOperators = [Equals, NotEquals, Contains, DoesNotContain];

export const ProductFilterDefinitions = [
    {
        label: 'Product ID',
        value: 'id',
        type: 'text' as IFilterType,
        operators: NumericalOperators,
        placeholder: 'Enter numbers (ex: 100)',
    },
    {
        label: 'Product Name',
        value: 'productName',
        type: 'text' as IFilterType,
        operators: TextOperators,
        placeholder: 'Enter text (ex: abc)',
    },
    {
        label: 'Product Origin',
        value: 'productOrigin',
        type: 'dropdown' as IFilterType,
        operators: [Equals, NotEquals],
        options: [
            { label: 'SA', value: 'SA' },
            { label: 'INDIA', value: 'INDIA' },
            { label: 'TURKEY', value: 'TURKEY' },
            { label: 'USA', value: 'USA' },
        ],
        placeholder: 'Select Country',
    },
    {
        label: 'Product Stock Status',
        value: 'productStockStatus',
        type: 'dropdown' as IFilterType,
        operators: [Equals, NotEquals],
        options: [
            { label: 'CUSTOM_ORDER', value: 'CUSTOM_ORDER' },
            { label: 'EX_STOCK', value: 'EX_STOCK' },
            { label: 'PARTIALLY_IN_STOCK', value: 'PARTIALLY_IN_STOCK' },
        ]
    },
    {
        label: 'Product Manufacturer',
        value: 'productManufacturer',
        type: 'text' as IFilterType,
        operators: TextOperators,
        placeholder: 'Enter text (ex: abc)',
    },
    {
        label: 'Vendors Name ID',
        value: 'vendorsNameId',
        type: 'text' as IFilterType,
        operators: NumericalOperators,
        placeholder: 'Enter numbers (ex: 100)',
    }
];
