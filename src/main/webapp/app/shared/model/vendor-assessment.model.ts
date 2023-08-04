import dayjs from 'dayjs';
import { IVendor } from 'app/shared/model/vendor.model';
import { Assessment } from 'app/shared/model/enumerations/assessment.model';

export interface IVendorAssessment {
  id?: number;
  assessmentDate?: string | null;
  assessedBY?: string | null;
  jobKnowledge?: keyof typeof Assessment | null;
  jobKnowledgeComment?: string | null;
  workQuality?: keyof typeof Assessment | null;
  workQualityComment?: string | null;
  attendancePunctuality?: keyof typeof Assessment | null;
  attendancePunctualityComment?: string | null;
  initiative?: keyof typeof Assessment | null;
  initiativeComment?: string | null;
  communicationListeningSkills?: keyof typeof Assessment | null;
  communicationListeningSkillsComment?: string | null;
  dependability?: keyof typeof Assessment | null;
  dependabilityComment?: string | null;
  vendorsName?: IVendor;
}

export const defaultValue: Readonly<IVendorAssessment> = {};
