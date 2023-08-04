import { IVendor } from 'app/shared/model/vendor.model';

export interface ISalesRepresentative {
  id?: number;
  fullName?: string | null;
  jobTitle?: string | null;
  email?: string | null;
  phone?: string | null;
  officeLocation?: string | null;
  addressLine1?: string | null;
  otherDetails?: string | null;
  vendorsName?: IVendor;
}

export const defaultValue: Readonly<ISalesRepresentative> = {};
