package domain

import (
	"time"

	"github.com/google/uuid"
)

type ResidentialComplex struct {
	ID uuid.UUID `json:"id" gorm:"type:uuid;default:gen_random_uuid();primaryKey"`

	Name string `json:"name,omitempty"`

	Street      string `json:"street"`
	HouseNumber string `json:"house_number"`
	ZipCode     string `json:"zip_code"`
	City        string `json:"city"`

	GeoRegionCode string    `json:"geo_region_code" gorm:"index"`
	GeoRegion     GeoRegion `json:"-" gorm:"foreignKey:GeoRegionCode;"`

	Lat float64 `json:"lat"`
	Lon float64 `json:"lon"`

	TotalApartments  int     `json:"total_apartments" gorm:"default:0"`
	AvgComplexRating float64 `json:"avg_complex_rating" gorm:"default:0"`

	CreatedAt  time.Time `json:"created_at"`
	ModifiedAt time.Time `json:"modified_at"`
}
