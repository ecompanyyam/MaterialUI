import React from 'react';
import { PDFDownloadLink, Document, Page, Text, View } from '@react-pdf/renderer';

const PDFDownloadComponent = ({ data, fields }) => {
  return (
    <PDFDownloadLink
      document={
        <Document>
          <Page>
            <View>
              {data.map(item => (
                <View key={item.id}>
                  {fields.map(field => (
                    <Text key={field.key}>{item[field.key]}</Text>
                  ))}
                </View>
              ))}
            </View>
          </Page>
        </Document>
      }
      fileName="data.pdf"
    >
      {({ blob, url, loading, error }) => (loading ? 'Loading...' : 'Download PDF')}
    </PDFDownloadLink>
  );
};

export default PDFDownloadComponent;
