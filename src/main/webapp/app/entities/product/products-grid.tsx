import React from 'react';
import { useNavigate } from 'react-router-dom';
import { DataGrid, GridActionsCellItem, GridCellParams, GridColDef, GridValueGetterParams, useGridApiRef } from '@mui/x-data-grid';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlinedIcon from '@mui/icons-material/DeleteOutlined';
import AttachmentOutlinedIcon from '@mui/icons-material/AttachmentOutlined';
import { IconButton } from '@mui/material';
import { openFile } from 'react-jhipster';
import { IProduct } from '../../shared/model/product.model';

type ProductGridProps = {
  rows: IProduct[];
};

export const ProductsGrid = ({ rows }: ProductGridProps) => {
  const datagridRef = useGridApiRef();
  const navigate = useNavigate();
  console.log('rows', rows);

  // React.useEffect(() => {
  //   datagridRef && datagridRef.current.autoSizeColumns();
  // }, []);

  const columnDefs: GridColDef[] = [
    {
      field: 'id',
      headerName: 'ID',
      width: 90,
    },
    {
      field: 'productName',
      headerName: 'Name',
      flex: 1,
    },
    {
      field: 'productRemark',
      headerName: 'Remark',
      flex: 1,
    },
    {
      field: 'productOrigin',
      headerName: 'Origin',
      flex: 1,
    },
    {
      field: 'productStockStatus',
      headerName: 'Stock Status',
      flex: 1,
    },
    {
      field: 'productAvgDeliveryTime',
      headerName: 'AVG Delivery Time',
      flex: 1,
    },
    {
      field: 'productManufacturer',
      headerName: 'Manufacturer',
      flex: 1,
    },
    {
      field: 'productImage',
      headerName: 'Image',
      sortable: false,
      // flex: 1,
      renderCell: (params) => {
        if (params.value) {
          return (
            <div>
              {params.row.productImageContentType ? (
                <a onClick={openFile(params.row.productImageContentType, params.row.productImage)}>
                  <img
                    src={`data:${params.row.productImageContentType};base64,${params.row.productImage}`}
                    style={{ maxHeight: '30px', maxWidth: '30px' }}
                  />
                  &nbsp;
                </a>
              ) : null}
              {/* <span>
                {`${params.row.productImageContentType}, ${byteSize(params.row.productImage)}`}
              </span> */}
          </div>
          )
        }
      },
    },
    {
      field: 'productAttachments',
      headerName: 'Attachments',
      sortable: false,
      // flex: 1,
      renderCell: (params) => {
        if (params.value) {
          return (
            <div>
              {params.row.productAttachmentsContentType ? (
                <IconButton onClick={openFile(params.row.productAttachmentsContentType, params.row.productAttachments)}>
                  <AttachmentOutlinedIcon />
                </IconButton>
                  // <a onClick={openFile(params.row.productAttachmentsContentType, params.row.productAttachments)}>
                  //   <Translate contentKey="entity.action.open">Open</Translate>
                  //   &nbsp;
                  // </a>
                ) : null
              }
            </div>
          )
        }
      }
    },
    {
      field: 'vendorsName.vendorNameEnglish',
      headerName: 'Vendor Name',
      valueGetter: (params: GridValueGetterParams) => {
        return params.row.vendorsName.vendorNameEnglish;
      },
      flex: 1,
    },
    {
      field: 'actions',
      type: 'actions',
      width: 80,
      getActions: (params) => {
        return [
          <GridActionsCellItem icon={<EditOutlinedIcon />} label='Edit' onClick={() => {
            // navigate(`/product/${params.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
            navigate(`/product/${params.id}/edit`);
          }}/>,
          <GridActionsCellItem icon={<DeleteOutlinedIcon />} label='Delete' onClick={() => {
            navigate(`/product/${params.id}/delete`)
          }}/>
        ]
      },
    }
  ];

  return (
    <DataGrid
      columns={columnDefs}
      rows={rows}
      initialState={{
        pagination: {
          paginationModel: { page: 0, pageSize: 10 },
        },
      }}
      disableColumnFilter
      apiRef={datagridRef}
      onCellClick={(params) => {
        if (params.field === 'vendorsName.vendorNameEnglish') {
          navigate(`/vendor/${params.row.vendorsName.id}`)
        } else if (['actions', 'productImage', 'productAttachments'].includes(params.field)) {
          return;
        } else {
          navigate(`/product/${params.id}`)
        }
      }}
      rowSelection={false}
    />
  );
};
