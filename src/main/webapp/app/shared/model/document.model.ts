import dayjs from 'dayjs';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

export interface IDocument {
  id?: number;
  documentType?: keyof typeof DocumentType | null;
  organizationName?: string | null;
  documentName?: string | null;
  documentNumber?: string | null;
  issueDate?: string | null;
  expiryDate?: string | null;
  documentStatus?: keyof typeof DocumentStatus | null;
  uploadFileContentType?: string | null;
  uploadFile?: string | null;
}

export const defaultValue: Readonly<IDocument> = {};
