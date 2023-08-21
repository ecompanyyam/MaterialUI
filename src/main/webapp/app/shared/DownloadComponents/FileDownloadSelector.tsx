import React, { useState } from 'react';
import { Button, ButtonGroup } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate } from 'react-jhipster';

import CSVDownloadComponent from './CSVDownloadComponent';
import ExcelDownloadComponent from './ExcelDownloadComponent';
import PDFDownloadComponent from './PDFDownloadComponent';

const FileDownloadSelector = ({ data, fields }) => {
  const [selectedFormat, setSelectedFormat] = useState(null);

  const handleDownload = format => {
    setSelectedFormat(format);
  };

  return (
    <>
      <ButtonGroup>
        <Button color="primary" onClick={() => handleDownload('csv')}>
          <FontAwesomeIcon icon="download" /> <Translate contentKey="entity.action.CSV" />
        </Button>
        <Button color="primary" onClick={() => handleDownload('excel')}>
          <FontAwesomeIcon icon="download" /> <Translate contentKey="entity.action.Excel" />
        </Button>
        <Button color="primary" onClick={() => handleDownload('pdf')}>
          <FontAwesomeIcon icon="download" /> <Translate contentKey="entity.action.PDF" />
        </Button>
      </ButtonGroup>

      {/* Render the selected download format */}
      {selectedFormat === 'csv' && <CSVDownloadComponent data={data} fields={fields} />}
      {selectedFormat === 'excel' && <ExcelDownloadComponent data={data} fields={fields} />}
      {selectedFormat === 'pdf' && <PDFDownloadComponent data={data} fields={fields} />}
    </>
  );
};

export default FileDownloadSelector;
