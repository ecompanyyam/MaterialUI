import React from 'react';
import * as XLSX from 'xlsx';

const ExcelDownloadComponent = ({ data, fields }) => {
  const handleDownload = () => {
    const ws = XLSX.utils.json_to_sheet(data, { header: fields });
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, 'data.xlsx');
  };

  return <button onClick={handleDownload}>Download Excel</button>;
};

export default ExcelDownloadComponent;
