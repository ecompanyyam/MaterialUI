import dayjs from 'dayjs';
import { ApprovalStatus } from 'app/shared/model/enumerations/approval-status.model';

export interface IClient {
  id?: number;
  clientName?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  height?: number | null;
  width?: number | null;
  taken?: string | null;
  uploaded?: string | null;
  dateOfSubmittal?: string | null;
  approvalStatus?: keyof typeof ApprovalStatus | null;
  registrationNumber?: string | null;
  dateOfRegistration?: string | null;
  dateOfExpiry?: string | null;
  approvalDocumentContentType?: string | null;
  approvalDocument?: string | null;
}

export const defaultValue: Readonly<IClient> = {};
