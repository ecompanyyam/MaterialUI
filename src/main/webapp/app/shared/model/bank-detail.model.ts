import { IVendor } from 'app/shared/model/vendor.model';

export interface IBankDetail {
  id?: number;
  bankAccount?: boolean | null;
  bankName?: string | null;
  branchSwiftCode?: string | null;
  ibanNo?: string | null;
  accountNumber?: string | null;
  vendorsName?: IVendor;
}

export const defaultValue: Readonly<IBankDetail> = {
  bankAccount: false,
};
