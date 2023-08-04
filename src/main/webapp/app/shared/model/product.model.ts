import dayjs from 'dayjs';
import { IVendor } from 'app/shared/model/vendor.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { StockStatus } from 'app/shared/model/enumerations/stock-status.model';
import { TimeTaken } from 'app/shared/model/enumerations/time-taken.model';

export interface IProduct {
  id?: number;
  productName?: string | null;
  productRemark?: string | null;
  productOrigin?: keyof typeof Country | null;
  productStockStatus?: keyof typeof StockStatus | null;
  productAvgDeliveryTime?: keyof typeof TimeTaken | null;
  productManufacturer?: string | null;
  productImageContentType?: string | null;
  productImage?: string | null;
  height?: number | null;
  width?: number | null;
  taken?: string | null;
  uploaded?: string | null;
  productAttachmentsContentType?: string | null;
  productAttachments?: string | null;
  vendorsName?: IVendor;
}

export const defaultValue: Readonly<IProduct> = {};
