import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CalculationInfoDetailsService } from 'app/entities/calculation-info-details/calculation-info-details.service';
import { ICalculationInfoDetails, CalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';

describe('Service Tests', () => {
  describe('CalculationInfoDetails Service', () => {
    let injector: TestBed;
    let service: CalculationInfoDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: ICalculationInfoDetails;
    let expectedResult: ICalculationInfoDetails | ICalculationInfoDetails[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CalculationInfoDetailsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CalculationInfoDetails(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CalculationInfoDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CalculationInfoDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CalculationInfoDetails', () => {
        const returnedFromService = Object.assign(
          {
            totalDebitAmount: 'BBBBBB',
            destinationAmount: 'BBBBBB',
            destinationExchangeRate: 'BBBBBB',
            destinationCurrencyIndicator: 'BBBBBB',
            discountAmount: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CalculationInfoDetails', () => {
        const returnedFromService = Object.assign(
          {
            totalDebitAmount: 'BBBBBB',
            destinationAmount: 'BBBBBB',
            destinationExchangeRate: 'BBBBBB',
            destinationCurrencyIndicator: 'BBBBBB',
            discountAmount: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CalculationInfoDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
