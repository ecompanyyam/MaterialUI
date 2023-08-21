import React from 'react';
import { CSVLink } from 'react-csv';

const CSVDownloadComponent = ({ data, fields }) => {
  return (
    <CSVLink data={data} headers={fields} filename={`data.csv`}>
      Download CSV
    </CSVLink>
  );
};

export default CSVDownloadComponent;
