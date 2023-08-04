import { IVendor } from 'app/shared/model/vendor.model';
import { IProduct } from 'app/shared/model/product.model';
import { FeedBackCategory } from 'app/shared/model/enumerations/feed-back-category.model';

export interface IFeedBackFromEmployee {
  id?: number;
  refContractPONumber?: string | null;
  feedBackCategory?: keyof typeof FeedBackCategory | null;
  comment?: string | null;
  vendorsName?: IVendor;
  productName?: IProduct;
}

export const defaultValue: Readonly<IFeedBackFromEmployee> = {};
