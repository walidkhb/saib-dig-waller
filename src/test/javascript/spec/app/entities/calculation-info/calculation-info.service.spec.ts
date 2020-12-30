import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CalculationInfoService } from 'app/entities/calculation-info/calculation-info.service';
import { ICalculationInfo, CalculationInfo } from 'app/shared/model/calculation-info.model';

describe('Service Tests', () => {
  describe('CalculationInfo Service', () => {
    let injector: TestBed;
    let service: CalculationInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICalculationInfo;
    let expectedResult: ICalculationInfo | ICalculationInfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CalculationInfoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CalculationInfo(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CalculationInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CalculationInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CalculationInfo', () => {
        const returnedFromService = Object.assign(
          {
            customerId: 'BBBBBB',
            beneficiaryId: 1,
            fromCurrency: 'BBBBBB',
            toCurrency: 'BBBBBB',
            transactionAmount: 1,
            transactionCurrency: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CalculationInfo', () => {
        const returnedFromService = Object.assign(
          {
            customerId: 'BBBBBB',
            beneficiaryId: 1,
            fromCurrency: 'BBBBBB',
            toCurrency: 'BBBBBB',
            transactionAmount: 1,
            transactionCurrency: 'BBBBBB',
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

      it('should delete a CalculationInfo', () => {
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
